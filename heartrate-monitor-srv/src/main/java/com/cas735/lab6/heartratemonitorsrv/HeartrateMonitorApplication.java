package com.cas735.lab6.heartratemonitorsrv;

import com.cas735.lab6.heartratemonitorsrv.RabbitConfiguration;

import java.beans.BeanProperty;

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
