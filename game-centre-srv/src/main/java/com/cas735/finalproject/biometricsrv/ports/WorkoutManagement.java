package com.cas735.finalproject.biometricsrv.ports;

import com.cas735.finalproject.biometricsrv.business.entities.Workout;

import java.time.LocalDateTime;

public interface WorkoutManagement {

    Workout create(String username, LocalDateTime startDateTime);
    void delete(Long workoutId);
    boolean endWorkout(Long workoutId, LocalDateTime endDateTime);

}
