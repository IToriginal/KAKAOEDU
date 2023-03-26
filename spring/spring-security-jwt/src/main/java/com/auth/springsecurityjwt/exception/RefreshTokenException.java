package com.auth.springsecurityjwt.exception;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;

public class RefreshTokenException extends RuntimeException {

    //에러의 종류를 나열형 상수로 정의
    public enum ErrorCase{
        NO_ACCESS, NO_REFRESH, OLD_REFRESH
    }

    private ErrorCase errorCase;

    //생성자
    public RefreshTokenException(ErrorCase errorCase){
        super(errorCase.name());
        this.errorCase = errorCase;
    }

    //메시지를 만드는 메서드
    public void sendResponseError(HttpServletResponse response){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        //DTO 객체 와 JSON 문자열 간의 변환을 수행해주는 객체
        Gson gson = new Gson();
        //Map을 JSON 문자열로 변환
        String responseStr =
            gson.toJson(Map.of("msg", errorCase.name(), "time",
                new Date()));
        //클라이언트에게 메시지를 전송
        try{
            response.getWriter().println(responseStr);
        }catch(IOException e){
            throw new RuntimeException();
        }
    }
}
