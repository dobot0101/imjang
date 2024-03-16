package com.dobot.imjang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class ImjangApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImjangApplication.class, args);
    }
}

