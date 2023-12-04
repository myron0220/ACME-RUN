package com.cas735.finalproject.biometricsrv.ports;

import com.cas735.finalproject.biometricsrv.business.entities.Heartrate;

public interface HeartrateManagement {

    Heartrate create(Heartrate heartrateRequest);
    void delete(Long heartrateId);
    void deleteByWorkoutId(Long workoutId);

}
