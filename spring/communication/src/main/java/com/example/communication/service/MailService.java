package com.example.communication.service;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    //메일을 보내기 위한 메서드
    public void sendMail(HttpServletRequest request) throws Exception{
        //예외가 발생하면 Spring이 처리하도록 예외를 넘김
        //파라미터 인코딩 설정
        request.setCharacterEncoding("utf-8");

        //보내는 사람 설정
        String setfrom = "kcs4.hat@gmail.com";

        //파라미터 읽기
//        String tomail = request.getParameter("tomail");
//        String title = request.getParameter("title");
//        String content = request.getParameter("content");

        String tomail = "gitshineit@gmail.com";
        String title = "비밀번호";
        String content = "";
        Random r = new Random();
        for (int i=0; i<10; i++) {
            int k = r.nextInt(26);
            content = content + (char) (k + 97);
        }
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(setfrom);
            message.setTo(tomail);
            message.setSubject(title);
            message.setText(content);

            //메일 전송
            javaMailSender.send(message);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
