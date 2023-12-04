package com.cas735.lab6.biometricsrv.business;

import com.cas735.lab6.biometricsrv.RabbitConfiguration;
import com.cas735.lab6.biometricsrv.ports.HeartrateFinder;
import com.cas735.lab6.biometricsrv.ports.HeartrateManagement;
import com.cas735.lab6.biometricsrv.ports.HeartrateRepository;
import com.cas735.lab6.biometricsrv.ports.HeartrateUpdate;
import com.cas735.lab6.biometricsrv.ports.WorkoutFinder;
import com.cas735.lab6.biometricsrv.ports.WorkoutManagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

import net.datafaker.Faker;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import com.cas735.lab6.biometricsrv.business.entities.Heartrate;
import com.cas735.lab6.biometricsrv.business.entities.Workout;

import org.springframework.beans.factory.annotation.Autowired;


@Service
@Slf4j
public class HeartrateRegistry implements HeartrateFinder {
    @Autowired
    HeartrateRepository heartrateRepository;

    public Heartrate findOneById(Long id) {
        return heartrateRepository.findById(id)
            .orElseThrow(() -> new HeartrateNotFoundException(id));
    }

    @Override
    public List<Heartrate> findAll() {
        return heartrateRepository.findAll();
    }

    @Override
    public List<Heartrate> findByUsername(String username) {
        return heartrateRepository.findByWorkout_Username(username);
    }

    @Override
    public List<Heartrate> findByWorkoutId(Long workoutId) {
        return heartrateRepository.findByWorkoutId(workoutId);
    }
    
}
