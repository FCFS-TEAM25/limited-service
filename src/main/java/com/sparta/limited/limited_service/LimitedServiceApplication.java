package com.sparta.limited.limited_service;

import com.sparta.limited.common_module.common.EnableCommonModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@SpringBootApplication
@EnableScheduling
@EnableCommonModule
@ComponentScan(basePackages = {
    "com.sparta.limited.limited_service",
    "com.sparta.limited.common_module.common.aop"
})
public class LimitedServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LimitedServiceApplication.class, args);
    }

}
