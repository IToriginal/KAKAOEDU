package com.auth.springsecurityjwt.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth.springsecurityjwt.domain.Member;

import lombok.extern.log4j.Log4j2;

/**
 * Sample Data 삽입
 */
@SpringBootTest
@Log4j2
public class MemberRepositoryTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("사용자 정보 샘플 데이터 삽입")
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                .memberId("Sample_Member" + i)
                .memberPassword(passwordEncoder.encode("1111"))
                .build();
            memberRepository.save(member);
        });
    }

}