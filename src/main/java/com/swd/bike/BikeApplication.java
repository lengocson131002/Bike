package com.swd.bike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class BikeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BikeApplication.class, args);
    }

    /**
     * Set UTC is default time zone in API server
     */
    @PostConstruct
    public void init(){
        // Setting Spring Boot SetTimeZone
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
