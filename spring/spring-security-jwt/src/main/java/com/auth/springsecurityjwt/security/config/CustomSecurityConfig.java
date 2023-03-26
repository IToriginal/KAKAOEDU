package com.auth.springsecurityjwt.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth.springsecurityjwt.security.filter.MemberLoginFilter;
import com.auth.springsecurityjwt.security.filter.RefreshTokenFilter;
import com.auth.springsecurityjwt.security.filter.TokenCheckFilter;
import com.auth.springsecurityjwt.service.handler.MemberLoginSuccessHandler;
import com.auth.springsecurityjwt.service.MemberDetailsService;
import com.auth.springsecurityjwt.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Security 설정 클래스
 */
@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class CustomSecurityConfig {

    private final MemberDetailsService memberDetailsService;
    private final JWTUtil jwtUtil;

    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil) {
        return new TokenCheckFilter(jwtUtil);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        log.info("🛠️ configure -------------------- 🛠️");

        //filter.MemberLoginFilter - AuthenticationManager 설정
        AuthenticationManagerBuilder authenticationManagerBuilder =
            httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(memberDetailsService)
            .passwordEncoder(passwordEncoder());

        //Get AuthenticationManager
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        httpSecurity.authenticationManager(authenticationManager);

        //MemberLoginFilter
        //Spring Security에서 username과 password를 처리하는 UsernamePasswordAuthenticationFilter의 앞쪽에서 동작하도록 설정
        MemberLoginFilter memberLoginFilter = new MemberLoginFilter("/generateToken");
        memberLoginFilter.setAuthenticationManager(authenticationManager);

        MemberLoginSuccessHandler successHandler = new MemberLoginSuccessHandler(jwtUtil);
        memberLoginFilter.setAuthenticationSuccessHandler(successHandler);

        //MemberLoginFilter 위치 조정
        httpSecurity.addFilterBefore(memberLoginFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.addFilterBefore(tokenCheckFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.addFilterBefore(new RefreshTokenFilter("/refreshToken", jwtUtil), TokenCheckFilter.class);

        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("🛠️ web configure -------------------- 🛠️");
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
