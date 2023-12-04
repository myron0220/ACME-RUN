package com.cas735.finalproject.heartratemonitorsrv.ports;

import com.cas735.finalproject.heartratemonitorsrv.business.entities.Workout;

import java.time.LocalDateTime;

public interface BiometricService {

    void sendHeartrate(Long workoutId, LocalDateTime dateTime, Integer heartrate);
    Workout createWorkout(String username, LocalDateTime startDateTime);
    void endWorkout(Long workoutId, LocalDateTime endDateTime);

}