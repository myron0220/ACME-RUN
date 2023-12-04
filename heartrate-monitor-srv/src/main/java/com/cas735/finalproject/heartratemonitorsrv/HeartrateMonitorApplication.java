package com.cas735.finalproject.heartratemonitorsrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class HeartrateMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeartrateMonitorApplication.class, args);
    }
}
