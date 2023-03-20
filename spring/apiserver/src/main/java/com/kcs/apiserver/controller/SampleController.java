package com.kcs.apiserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/sample")
public class SampleController {
    @GetMapping("/itoriginal")
    public List<String> itoriginal(){
        return Arrays.asList("Java", "Spring", "Python");
    }
}
