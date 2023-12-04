package com.cas735.lab6.heartratemonitorsrv.ports;

import com.cas735.lab6.heartratemonitorsrv.business.entities.Workout;
import com.cas735.lab6.heartratemonitorsrv.dto.CreateWorkoutRequest;
import com.cas735.lab6.heartratemonitorsrv.dto.EndWorkoutRequest;

import java.time.LocalDateTime;

import com.cas735.lab6.heartratemonitorsrv.business.entities.Heartrate;

public interface BiometricService {

    void sendHeartrate(Long workoutId, LocalDateTime dateTime, Integer heartrate);
    Workout createWorkout(String username, LocalDateTime startDateTime);
    void endWorkout(Long workoutId, LocalDateTime endDateTime);

}