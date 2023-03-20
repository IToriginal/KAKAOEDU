package com.kcs.apiserver.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

    //Password 암호화를 위해서 필요
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    //Security가 동작할 때 같이 동작할 필터를 등록하는 메서드
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("🛠️configure -------------------- 🛠");
        //csrf 기능 중지
        http.csrf().disable();
        //세션 사용 중지
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //모든 설정을 빌드해서 리턴
        return http.build();
    }

    //웹에서 시큐리티 적용 설정 - 정적 파일은 security 적용 대상이 아님
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("🛠️web configure -------------------- 🛠");
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
