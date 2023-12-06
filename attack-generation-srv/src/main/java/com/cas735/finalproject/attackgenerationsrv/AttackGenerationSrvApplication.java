package com.cas735.finalproject.attackgenerationsrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AttackGenerationSrvApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttackGenerationSrvApplication.class, args);
    }

}
