package com.cas735.finalproject.biometricsrv.ports;

import com.cas735.finalproject.biometricsrv.business.entities.Workout;

import java.util.List;

public interface WorkoutFinder {

    List<Workout> findAll();
    Workout findOneById(Long workoutId);
    List<Workout> findByUsername(String username);

}
