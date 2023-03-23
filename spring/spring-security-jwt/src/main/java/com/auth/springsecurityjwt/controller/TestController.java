package com.auth.springsecurityjwt.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller 역할을 수행할 Sample
 */
@RestController
@RequestMapping("/api/sample")
public class TestController {

    @GetMapping("/test")
    public List<String> test() {
        return Arrays.asList("테스트1", "테스트2", "테스트3");
    }
}
