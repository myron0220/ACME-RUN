package com.cas735.lab6.biometricsrv.business;

import com.cas735.lab6.biometricsrv.RabbitConfiguration;
import com.cas735.lab6.biometricsrv.ports.HeartrateFinder;
import com.cas735.lab6.biometricsrv.ports.HeartrateManagement;
import com.cas735.lab6.biometricsrv.ports.HeartrateRepository;
import com.cas735.lab6.biometricsrv.ports.HeartrateUpdate;
import com.cas735.lab6.biometricsrv.ports.WorkoutFinder;
import com.cas735.lab6.biometricsrv.ports.WorkoutManagement;
import com.cas735.lab6.biometricsrv.ports.WorkoutRepository;

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
import com.cas735.lab6.biometricsrv.business.WorkoutNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;


@Service
@Slf4j
public class HeartrateManager implements HeartrateManagement {
    @Autowired
    private HeartrateRepository heartrateRepository;
    @Autowired
    private HeartrateFinder heartrateFinder;
    @Autowired
    private WorkoutFinder workoutFinder;

    @Override
    public Heartrate create(Heartrate heartrateRequest) {
        // see https://www.bezkoder.com/jpa-one-to-many/
        Workout workout = workoutFinder.findOneById(heartrateRequest.getWorkoutId());
        
        if (workout == null)
            throw new WorkoutNotFoundException(heartrateRequest.getWorkoutId());
        
        return heartrateRepository.save(heartrateRequest);
    }

    @Override
    public void delete(Long heartrateId) {
        heartrateRepository.deleteById(heartrateId);
    }
    
    @Override
    public void deleteByWorkoutId(Long workoutId) {
        heartrateRepository.deleteByWorkoutId(workoutId);
    }
    
}
