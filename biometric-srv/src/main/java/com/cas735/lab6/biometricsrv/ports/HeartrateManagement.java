package com.cas735.lab6.biometricsrv.ports;

import com.cas735.lab6.biometricsrv.business.entities.Heartrate;

import java.time.LocalDateTime;

public interface HeartrateManagement {

    Heartrate create(Heartrate heartrateRequest);
    void delete(Long heartrateId);
    void deleteByWorkoutId(Long workoutId);

}
