package com.cas735.finalproject.heartratemonitorsrv.ports;

import com.cas735.finalproject.heartratemonitorsrv.business.entities.Location;
import com.cas735.finalproject.heartratemonitorsrv.business.entities.Trail;
import com.cas735.finalproject.heartratemonitorsrv.business.entities.Workout;

import java.time.LocalDateTime;

public interface HeartrateService {

    void sendHeartrate(Long workoutId, LocalDateTime dateTime, Integer heartrate, Double latitude, Double longitude);
    Workout createWorkout(String username, LocalDateTime startDateTime);
    void endWorkout(Long workoutId, LocalDateTime endDateTime);
    Trail requestTrailAllocation(String userName, Location location);
}