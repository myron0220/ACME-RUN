package com.cas735.lab6.biometricsrv;

import com.cas735.lab6.biometricsrv.business.entities.Workout;

import java.beans.BeanProperty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BiometricApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiometricApplication.class, args);
    }
}
