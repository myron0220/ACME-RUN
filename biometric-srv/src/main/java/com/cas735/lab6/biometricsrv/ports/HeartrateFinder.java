package com.cas735.lab6.biometricsrv.ports;

import com.cas735.lab6.biometricsrv.business.entities.Heartrate;
import com.cas735.lab6.biometricsrv.business.entities.Workout;

import java.time.LocalDateTime;
import java.util.List;

public interface HeartrateFinder {

    Heartrate findOneById(Long id);
    List<Heartrate> findAll();
    List<Heartrate> findByUsername(String username);
    List<Heartrate> findByWorkoutId(Long workoutId);

}
