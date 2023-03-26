package com.auth.springsecurityjwt.security.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.filter.OncePerRequestFilter;

import com.auth.springsecurityjwt.exception.AccessTokenException;
import com.auth.springsecurityjwt.util.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Access Token 검증 필터: 하나의 요청에 대해서 한 번씩 동작하는 필터를 상속
 */
@Log4j2
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    //토큰의 유효성 검사를 위한 메서드
    private Map<String, Object> validateAccessToken(HttpServletRequest request) {
        //Header에서 토큰을 얻기
        String headerStr = request.getHeader("Authorization");

        //Token에 Bearer라는 문자열을 포함시켜서 전송 => 토큰이 있으면 8자
        if (headerStr == null || headerStr.length() < 8) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }
        //실제 토큰 가져오기
        String tokenType = headerStr.substring(0, 6); //Bearer
        String tokenStr = headerStr.substring(7); //실제 토큰의 문자열

        //타입 검사
        if (tokenType.equalsIgnoreCase("Bearer") == false) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        try {
            //토큰의 유효성 검사
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            return values;
        } catch(MalformedJwtException malformedJwtException){
            log.error("🚨 MalformedJwtException -------------------- 🚨");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        }catch(SignatureException signatureException){
            log.error("🚨 SignatureException -------------------- 🚨");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        }catch(ExpiredJwtException expiredJwtException){
            log.error("🚨 ExpiredJwtException -------------------- 🚨");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
    }

    //필터로 동작하는 메서드
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws IOException, ServletException {
        String path = request.getRequestURI();
        if (!path.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("🛠️ Token Check Filter -------------------- 🛠️");
        log.info("💡 JWTUtil =====> " + jwtUtil);

        try {
            //토큰 유효성 검사
            validateAccessToken(request);
            //다음 Filter에 처리를 넘겨줌 - AOP - Filter(Interceptor, AOP)
            filterChain.doFilter(request, response);
        } catch (AccessTokenException accessTokenException) {
            accessTokenException.sendResponseError(response);
        }
    }
}
