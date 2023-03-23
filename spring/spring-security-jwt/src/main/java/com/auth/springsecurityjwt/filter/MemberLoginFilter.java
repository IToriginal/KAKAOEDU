package com.auth.springsecurityjwt.filter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * 토큰 인증을 위한 Security Filter
 */
@Log4j2
public class MemberLoginFilter extends AbstractAuthenticationProcessingFilter {

    public MemberLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    //파라미터를 읽어서 Map으로 만들어주는 메서드
    private Map<String, String> parseRequestJSON(HttpServletRequest request) {

        //JSON 데이터를 분석해 memberId, memberPassword 전달 값을 Map으로 처리
        try (Reader reader = new InputStreamReader(request.getInputStream())) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Map.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException {

        log.info("🛠️ MemberLoginFilter -------------------- 🛠️");

        if (request.getMethod().equalsIgnoreCase("GET")) {

            log.info("============ GET METHOD NOT SUPPORT ============");
            return null;
        }
        //토큰 생성 요청을 했을 때 아이디와 비밀번호를 가져와서 Map으로 생성
        Map<String, String> jsonData = parseRequestJSON(request);
        log.info("💡 jsonData =====> " + jsonData);

        //아이디와 비밀번호를 필터에 전송해서 사용하도록 설정 - MemberDetailsService 동작
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            jsonData.get("memberId"), jsonData.get("memberPassword"));

        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
