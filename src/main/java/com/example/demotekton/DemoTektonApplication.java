package com.example.demotekton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class DemoTektonApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoTektonApplication.class, args);
    }

}
