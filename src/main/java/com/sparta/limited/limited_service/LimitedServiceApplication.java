package com.sparta.limited.limited_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@SpringBootApplication
@EnableScheduling
public class LimitedServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LimitedServiceApplication.class, args);
    }

}
