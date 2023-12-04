package com.cas735.finalproject.biometricsrv.business;

import com.cas735.finalproject.biometricsrv.ports.HeartrateFinder;
import com.cas735.finalproject.biometricsrv.ports.HeartrateManagement;
import com.cas735.finalproject.biometricsrv.ports.HeartrateRepository;
import com.cas735.finalproject.biometricsrv.ports.WorkoutFinder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.cas735.finalproject.biometricsrv.business.entities.Heartrate;
import com.cas735.finalproject.biometricsrv.business.entities.Workout;

import org.springframework.beans.factory.annotation.Autowired;


@Service
@Slf4j
public class HeartrateManager implements HeartrateManagement {
    @Autowired
    private HeartrateRepository heartrateRepository;
    @Autowired
    private HeartrateFinder heartrateFinder;
    @Autowired
    private WorkoutFinder workoutFinder;

    @Override
    public Heartrate create(Heartrate heartrateRequest) {
        // see https://www.bezkoder.com/jpa-one-to-many/
        Workout workout = workoutFinder.findOneById(heartrateRequest.getWorkoutId());
        
        if (workout == null)
            throw new WorkoutNotFoundException(heartrateRequest.getWorkoutId());
        
        return heartrateRepository.save(heartrateRequest);
    }

    @Override
    public void delete(Long heartrateId) {
        heartrateRepository.deleteById(heartrateId);
    }
    
    @Override
    public void deleteByWorkoutId(Long workoutId) {
        heartrateRepository.deleteByWorkoutId(workoutId);
    }
    
}
