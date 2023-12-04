package com.cas735.lab6.biometricsrv.ports;

import com.cas735.lab6.biometricsrv.business.entities.Workout;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutFinder {

    List<Workout> findAll();
    Workout findOneById(Long workoutId);
    List<Workout> findByUsername(String username);

}
