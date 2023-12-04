package com.cas735.finalproject.gpsmonitorsrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class GPSMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GPSMonitorApplication.class, args);
    }
}
