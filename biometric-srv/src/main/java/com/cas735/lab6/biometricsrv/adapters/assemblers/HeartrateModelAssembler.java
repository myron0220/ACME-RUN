package com.cas735.lab6.biometricsrv.adapters.assemblers;

import com.cas735.lab6.biometricsrv.adapters.rest.WorkoutHateoasController;
import com.cas735.lab6.biometricsrv.adapters.rest.HeartrateHateoasController;
import com.cas735.lab6.biometricsrv.business.entities.Heartrate;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class HeartrateModelAssembler
        implements RepresentationModelAssembler<Heartrate, EntityModel<Heartrate>> {

    @Override
    public EntityModel<Heartrate> toModel(Heartrate entity) {
        Link self = linkTo(methodOn(HeartrateHateoasController.class)
                .findOneById(entity.getId())).withSelfRel();
        Link root = linkTo(methodOn(HeartrateHateoasController.class)
                .findAll()).withRel("heartrates");
        Link workoutHeartrates = linkTo(methodOn(HeartrateHateoasController.class)
                .findByWorkoutId(entity.getWorkoutId())).withRel("heartrates");
        Link workout = linkTo(methodOn(WorkoutHateoasController.class)
                .findOneById(entity.getWorkoutId())).withRel("workout");
        
        return EntityModel.of(entity, self, root, workoutHeartrates, workout);
    }
}
