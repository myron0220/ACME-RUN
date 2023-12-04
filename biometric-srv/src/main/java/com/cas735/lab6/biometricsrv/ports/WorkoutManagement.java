package com.cas735.lab6.biometricsrv.ports;

import com.cas735.lab6.biometricsrv.business.entities.Workout;

import java.time.LocalDateTime;

public interface WorkoutManagement {

    Workout create(String username, LocalDateTime startDateTime);
    void delete(Long workoutId);
    boolean endWorkout(Long workoutId, LocalDateTime endDateTime);

}
