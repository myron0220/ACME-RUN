package com.cas735.lab6.biometricsrv.ports;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import com.cas735.lab6.biometricsrv.business.entities.Heartrate;
import com.cas735.lab6.biometricsrv.dto.DeletionRequest;

public interface HeartrateHateoas {

    CollectionModel<EntityModel<Heartrate>> findAll();
    EntityModel<Heartrate> findOneById(Long id);
    CollectionModel<EntityModel<Heartrate>> findByUsername(String username);
    CollectionModel<EntityModel<Heartrate>> findByWorkoutId(Long workoutId);
    EntityModel<Heartrate> create(Heartrate heartrateRequest);
    void delete(DeletionRequest del, Long id);
    void deleteByWorkoutId(DeletionRequest del, Long workoutId);

}
