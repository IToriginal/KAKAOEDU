package com.example.communication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CustomServletConfig implements WebMvcConfigurer {
    //addResourcesHandlers(ResourceHandlerRegistry registry)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //static한 html 파일의 경로를 설정
        //files/ 요청경로 로 요청하면 static 디렉토리에서 파일을 가져와서 출력
        registry.addResourceHandler("/files/**")
                .addResourceLocations("classpath:/static/");
    }
}
