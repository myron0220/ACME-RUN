package com.cas735.finalproject.biometricsrv.business;

import com.cas735.finalproject.biometricsrv.ports.WorkoutFinder;
import com.cas735.finalproject.biometricsrv.ports.WorkoutRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import com.cas735.finalproject.biometricsrv.business.entities.Workout;

import org.springframework.beans.factory.annotation.Autowired;


@Service
@Slf4j
public class WorkoutRegistry implements WorkoutFinder {
    @Autowired
    WorkoutRepository workoutRepository;

    @Override
    public List<Workout> findAll() {
        return workoutRepository.findAll();
    }

    @Override
    public Workout findOneById(Long workoutId) {
        return workoutRepository.findById(workoutId)
            .orElseThrow(() -> new WorkoutNotFoundException(workoutId));
    }
    
    @Override
    public List<Workout> findByUsername(String username) {
        return workoutRepository.findByUsername(username);
    }

}
