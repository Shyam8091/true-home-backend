package com.example.true_home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients(basePackages = "com.example.true_home.feign")
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class TrueHomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrueHomeApplication.class, args);
    }

}
