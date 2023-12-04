package com.cas735.lab6.biometricsrv.ports;

import com.cas735.lab6.biometricsrv.business.entities.Heartrate;
import com.cas735.lab6.biometricsrv.business.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

import java.util.List;

public interface HeartrateRepository extends JpaRepository<Heartrate, Long> {

    List<Heartrate> findByWorkoutId(Long workoutId);

    @Transactional
    void deleteByWorkoutId(Long workoutId);

    @Transactional
    List<Heartrate> findByWorkout_Username(String username);
    
}
