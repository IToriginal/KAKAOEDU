package com.auth.springsecurityjwt.security.filter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth.springsecurityjwt.exception.RefreshTokenException;
import com.auth.springsecurityjwt.util.JWTUtil;
import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * /refreshToken 경로와 JWTUtil 인스턴스를 주입받아서
 * 해당 경로가 아닌 경우에는 다음 순서의 필터가 실행되도록 작성
 */
@Log4j2
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final String refreshPath;
    private final JWTUtil jwtUtil;

    private Map<String, String> parseRequestJSON(HttpServletRequest request) {

        //JSON 데이터를 분석해서 memberId, memberPassword 전달 값을 Map으로 처리
        try (Reader reader = new InputStreamReader(request.getInputStream())) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Map.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    //AccessToken 검증
    private void checkAccessToken(String accessToken) throws RefreshTokenException {
        try {
            jwtUtil.validateToken(accessToken);
        } catch (ExpiredJwtException expiredJwtException) {
            log.info("🛠️ Access Token has expired -------------------- ❌");
        } catch (Exception exception) {
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS);
        }
    }

    //RefreshToken 검증
    private Map<String, Object> checkRefreshToken(String refreshToken) throws RefreshTokenException {
        try {
            Map<String, Object> values = jwtUtil.validateToken(refreshToken);
            return values;
        } catch (ExpiredJwtException expiredJwtException) {
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
        } catch (MalformedJwtException malformedJwtException) {
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        } catch (Exception exception) {
            new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }
        return null;
    }

    //Token을 전송하는 메서드
    private void sendTokens(String accessTokenValue, String refreshTokenValue, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Gson gson = new Gson();

        String jsonStr = gson.toJson(Map.of("accessToken", accessTokenValue, "refreshToken", refreshTokenValue));
        try {
            response.getWriter().println(jsonStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Filter로 동작하는 메서드
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        //클라이언트의 요청 URL
        String path = request.getRequestURI();
        //URL이 다르면 다음 필터로 진행
        if (!path.equals(refreshPath)) {
            log.info("🛠️ Skip Refresh Token Filter -------------------- 🛠️");
            //다음 필터로 진행
            filterChain.doFilter(request, response);
            //필터의 동작을 중지 시키기 위한 return
            return;
        }
        log.info("🛠️ Refresh Token Filter -------------------- Run 🛠️");

        //전송된 JSON에서 accessToken과 refreshToken을 얻어옴
        Map<String, String> tokens = parseRequestJSON(request);

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        log.info("💡 accessToken =====> " + accessToken);
        log.info("💡 refreshToken =====> " + refreshToken);

        try {
            checkAccessToken(accessToken);
        } catch (RefreshTokenException refreshTokenException) {
            refreshTokenException.sendResponseError(response);
            return;
        }

        Map<String, Object> refreshClaims = null;
        try {
            refreshClaims = checkRefreshToken(refreshToken);
            log.info("💡 refreshClaims =====> " + refreshClaims);
        } catch (RefreshTokenException refreshTokenException) {
            refreshTokenException.sendResponseError(response);
            return;
        }

        //Refresh Token의 유효시간이 얼마 남지 않은 경우
        Integer exp = (Integer)refreshClaims.get("exp");

        Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);
        Date current = new Date(System.currentTimeMillis());

        //만료 시간과 현재시간의 간격을 계산
        //3일 미만의 경우 RefreshToken을 다시 생성
        long gapTime = (expTime.getTime() - current.getTime());

        log.info("💡 current =====> " + current);
        log.info("💡 expTime =====> " + expTime);
        log.info("💡 gapTime =====> " + gapTime);

        String memberId = (String)refreshClaims.get("memberId");

        String accessTokenValue = jwtUtil.generateToken(Map.of("memberId", memberId), 1);
        String refreshTokenValue = tokens.get("refreshToken");

        //RefreshToken의 기한이 3일 이하인 경우
        if (gapTime < (1000 * 60 * 3)) {
            log.info("🛠️ New Refresh Token Required -------------------- 🛠️");
            refreshTokenValue = jwtUtil.generateToken(Map.of("memberId", memberId), 30);
        }

        log.info("🛠️ Refresh Token Result -------------------- 🛠️");
        log.info("💡 AccessToken =====> " + accessTokenValue);
        log.info("💡 RefreshToken =====> " + refreshTokenValue);

        sendTokens(accessTokenValue, refreshTokenValue, response);

    }

}
