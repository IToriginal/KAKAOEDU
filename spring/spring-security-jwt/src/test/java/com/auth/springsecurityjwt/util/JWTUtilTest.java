package com.auth.springsecurityjwt.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class JWTUtilTest {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    @DisplayName("비밀키 생성 확인")
    public void testGenerateKey() {

        Map<String, Object> claimMap = Map.of("memberId", "Sample_Member10");
        String jwtStr = jwtUtil.generateToken(claimMap, 10);
        System.out.println(jwtStr);
    }

    @Test
    @DisplayName("유효 토큰 확인")
    public void testValidate() {
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk4MjMwMDIsImlhdCI6MTY3OTgxNzAwMiwibWVtYmVySWQiOiJTYW1wbGVfTWVtYmVyMTAifQ.Oi94GqdhGfTx8MljZKw5XcSgOhSsntcwRTghh4N-ON4";
        Map<String, Object> claim = jwtUtil.validateToken(jwt);
        System.out.println(claim);
    }

    @Test
    public void testAll() {
        String jwtStr = jwtUtil.generateToken(Map.of("memberId", "Sample_Member10", "email", "sample10@sample.com"), 1);
        System.out.println(jwtStr);
        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);
        System.out.println("MEMBERID: " + claim.get("memberId"));
        System.out.println("EMAIL: " + claim.get("email"));
    }
}