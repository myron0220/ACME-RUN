package com.cas735.finalproject.biometricsrv.ports;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.cas735.finalproject.biometricsrv.dto.CreateWorkoutRequest;
import com.cas735.finalproject.biometricsrv.dto.EndWorkoutRequest;
import com.cas735.finalproject.biometricsrv.business.entities.Workout;
import com.cas735.finalproject.biometricsrv.dto.DeletionRequest;

public interface WorkoutHateoas {

    CollectionModel<EntityModel<Workout>> findAll();
    EntityModel<Workout> findOneById(Long id);
    CollectionModel<EntityModel<Workout>> findByUsername(String username);
    EntityModel<Workout> create(CreateWorkoutRequest workoutRequest);
    EntityModel<Workout> finish(EndWorkoutRequest req, Long id);
    void delete(DeletionRequest del, Long id);

}
