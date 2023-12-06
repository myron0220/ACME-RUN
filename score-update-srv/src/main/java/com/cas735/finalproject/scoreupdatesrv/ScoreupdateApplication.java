package com.cas735.finalproject.scoreupdatesrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScoreupdateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoreupdateApplication.class, args);
    }
}

