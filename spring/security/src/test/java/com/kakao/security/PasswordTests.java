package com.kakao.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testEnCode() {
        String passsword = "1111";
        String enPwd = passwordEncoder.encode(passsword);
        System.out.println("enPwd: " + enPwd);
        enPwd = passwordEncoder.encode(passsword);
        System.out.println("enPwd: " + enPwd);

        boolean result = passwordEncoder.matches(passsword, enPwd);
        System.out.println("result:" + result);
    }
}
