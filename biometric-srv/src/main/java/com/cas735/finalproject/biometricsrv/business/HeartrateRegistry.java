package com.cas735.finalproject.biometricsrv.business;

import com.cas735.finalproject.biometricsrv.ports.HeartrateFinder;
import com.cas735.finalproject.biometricsrv.ports.HeartrateRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import com.cas735.finalproject.biometricsrv.business.entities.Heartrate;

import org.springframework.beans.factory.annotation.Autowired;


@Service
@Slf4j
public class HeartrateRegistry implements HeartrateFinder {
    @Autowired
    HeartrateRepository heartrateRepository;

    public Heartrate findOneById(Long id) {
        return heartrateRepository.findById(id)
            .orElseThrow(() -> new HeartrateNotFoundException(id));
    }

    @Override
    public List<Heartrate> findAll() {
        return heartrateRepository.findAll();
    }

    @Override
    public List<Heartrate> findByUsername(String username) {
        return heartrateRepository.findByWorkout_Username(username);
    }

    @Override
    public List<Heartrate> findByWorkoutId(Long workoutId) {
        return heartrateRepository.findByWorkoutId(workoutId);
    }
    
}
