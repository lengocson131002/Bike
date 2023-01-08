package com.swd.bike;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${test.message}")
    private String message;

    @Value("${test.secret}")
    private String secret;

    @GetMapping("/message")
    public String message() {
        return message;
    }

    @GetMapping("/secret")
    public String secret() {
        return secret;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
