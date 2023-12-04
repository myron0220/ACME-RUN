package com.cas735.lab6.biometricsrv.ports;

import com.cas735.lab6.biometricsrv.business.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUsername(String username);
    
}
