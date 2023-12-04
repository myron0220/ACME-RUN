package com.cas735.finalproject.biometricsrv.ports;

import com.cas735.finalproject.biometricsrv.business.entities.Heartrate;

import java.util.List;

public interface HeartrateFinder {

    Heartrate findOneById(Long id);
    List<Heartrate> findAll();
    List<Heartrate> findByUsername(String username);
    List<Heartrate> findByWorkoutId(Long workoutId);

}
