package com.example.communication.controller;

import com.example.communication.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class ChatController {

    @GetMapping("/")
    public String chat() {
        return "chatting";
    }

    @GetMapping("textmail")
    public void textmail(){}

    private final MailService mailService;
    @PostMapping("textmail")
    public String textmail(HttpServletRequest request) throws Exception{
        mailService.sendMail(request);
        return "redirect:/";
    }
}
