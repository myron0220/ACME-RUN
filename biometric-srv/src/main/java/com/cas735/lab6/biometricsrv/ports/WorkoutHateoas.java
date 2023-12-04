package com.cas735.lab6.biometricsrv.ports;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.cas735.lab6.biometricsrv.dto.CreateWorkoutRequest;
import com.cas735.lab6.biometricsrv.dto.EndWorkoutRequest;
import com.cas735.lab6.biometricsrv.business.entities.Workout;
import com.cas735.lab6.biometricsrv.dto.DeletionRequest;

public interface WorkoutHateoas {

    CollectionModel<EntityModel<Workout>> findAll();
    EntityModel<Workout> findOneById(Long id);
    CollectionModel<EntityModel<Workout>> findByUsername(String username);
    EntityModel<Workout> create(CreateWorkoutRequest workoutRequest);
    EntityModel<Workout> finish(EndWorkoutRequest req, Long id);
    void delete(DeletionRequest del, Long id);

}
