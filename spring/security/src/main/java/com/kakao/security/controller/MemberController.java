package com.kakao.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/login")
    //error는 로그인 실패했을 때의 파라미터
    //logout은 로그아웃 한 후 로드인으로 이동했을 때의 파라미터
    public void login(String error, String logout){
        if(error != null){

        }
        if(logout != null){

        }
    }
}
