package com.cas735.finalproject.biometricsrv.business;

import com.cas735.finalproject.biometricsrv.ports.WorkoutFinder;
import com.cas735.finalproject.biometricsrv.ports.WorkoutManagement;
import com.cas735.finalproject.biometricsrv.ports.WorkoutRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.cas735.finalproject.biometricsrv.business.entities.Workout;

import org.springframework.beans.factory.annotation.Autowired;

@Service
@Slf4j
public class WorkoutManager implements WorkoutManagement {
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private WorkoutFinder workoutFinder;

    @Override
    public Workout create(String username, LocalDateTime startDateTime) {
        Workout newWorkout = new Workout(username, startDateTime);

        workoutRepository.save(newWorkout);

        return newWorkout;
    }

    @Override
    public boolean endWorkout(Long workoutId, LocalDateTime endDateTime) {
        Workout workout = workoutFinder.findOneById(workoutId);

        if (workout == null)
            return false;

        workout.setEndTime(endDateTime);

        workoutRepository.save(workout);

        return true;
    }

    @Override 
    public void delete(Long workoutId) {
        workoutRepository.deleteById(workoutId);
    }
}
