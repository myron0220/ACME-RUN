package com.cas735.lab6.biometricsrv.business;

import com.cas735.lab6.biometricsrv.RabbitConfiguration;
import com.cas735.lab6.biometricsrv.ports.HeartrateFinder;
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

import org.springframework.beans.factory.annotation.Autowired;

@Service
@Slf4j
public class WorkoutManager implements WorkoutManagement {
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private WorkoutFinder workoutFinder;

    @Override
    public Workout create(String username, LocalDateTime startDateTime) {
        Workout newWorkout = new Workout(username, startDateTime);

        workoutRepository.save(newWorkout);

        return newWorkout;
    }

    @Override
    public boolean endWorkout(Long workoutId, LocalDateTime endDateTime) {
        Workout workout = workoutFinder.findOneById(workoutId);

        if (workout == null)
            return false;

        workout.setEndTime(endDateTime);

        workoutRepository.save(workout);

        return true;
    }

    @Override 
    public void delete(Long workoutId) {
        workoutRepository.deleteById(workoutId);
    }
}
