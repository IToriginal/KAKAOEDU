package com.auth.springsecurityjwt.handler;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

/**
 * 로그인 인증 성공 작업 처리
 */
@Log4j2
@RequiredArgsConstructor
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException {

        log.info("🛠️ Login Success Handler -------------------- 🛠️");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
