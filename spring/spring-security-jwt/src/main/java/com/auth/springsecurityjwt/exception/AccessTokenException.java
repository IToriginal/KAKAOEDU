package com.auth.springsecurityjwt.exception;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.http.MediaType;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;

/**
 * AccessToken 검증 - 예외
 */
public class AccessTokenException extends RuntimeException{
    TOKEN_ERROR token_error;

    public enum TOKEN_ERROR{
        UNACCEPT(401, "Token is null or too short"),
        BADTYPE(401, "TOken type Bearer"),
        MALFORM(403, "Malformed Token"),
        BADSIGN(403, "BadSignatured Token"),
        EXPIRED(403, "Expired Token");

        private int status;
        private String msg;
        TOKEN_ERROR(int status, String msg){
            this.status = status;
            this.msg = msg;
        }

        public int getStatus(){
            return this.status;
        }
        public String getMsg(){
            return this.msg;
        }
    }

    //생성자
    public AccessTokenException(TOKEN_ERROR error){
        //예외 이름 변경
        super(error.name());
        this.token_error = error;
    }

    //에러 전송 메서드
    public void sendResponseError(HttpServletResponse response){
        response.setStatus(token_error.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();
        String responseStr = gson.toJson(Map.of("msg", token_error.getMsg(),
            "time", new Date()));

        try{
            response.getWriter().println(responseStr);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
