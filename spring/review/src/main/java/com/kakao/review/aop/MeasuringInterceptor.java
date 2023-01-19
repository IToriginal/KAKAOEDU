package com.kakao.review.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
public class MeasuringInterceptor implements HandlerInterceptor {

    // Controller에게 요청하기 전에 호출되는 메서드
    // false로 리턴하면 Controller에게 요청을 전달하지 않는다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.warn("[preHandle] Before Controller Request");
        return true;
    }

    // Controller가 요청을 정상적으로 처리한 후 호출되는 메서드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.warn("[postHandle] after Controller Request Complete");
    }

    // Controller가 요청을 정상적으로 처리한 후 무조건 호출되는 메서드
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.warn("[afterCompletion] after Controller must Request Complete");
    }

}
