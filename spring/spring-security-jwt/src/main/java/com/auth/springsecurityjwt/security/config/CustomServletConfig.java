package com.auth.springsecurityjwt.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * config package에 web 설정을 위한 설정
 */
@Configuration
@EnableWebMvc
public class CustomServletConfig implements WebMvcConfigurer {


    //클라이언트에서 자원을 요청할 때 처리할 메서드
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            //files로 요청하면 static 디렉토리에서 뷰를 찾아오는 설정 (React 사용시 경로를 변경?)
            .addResourceHandler("/files/**")
            .addResourceLocations("classpath:/static/");
    }
}
