package com.kakao.review.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private MovieService movieService;

    @Test
    public void getMovie(){
        System.out.println(movieService.getMovie(102L));
    }
}
