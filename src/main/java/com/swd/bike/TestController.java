package com.swd.bike;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${test.message}")
    private String test;

    @Value("${test.secret}")
    private String secret;

    @GetMapping("/test")
    public String test() {
        return "test" + " " + test + " " + secret;
    }


    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }
}
