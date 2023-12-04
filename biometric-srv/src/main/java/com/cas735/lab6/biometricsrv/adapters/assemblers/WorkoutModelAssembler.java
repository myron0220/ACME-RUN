package com.cas735.lab6.biometricsrv.adapters.assemblers;

import com.cas735.lab6.biometricsrv.adapters.rest.HeartrateHateoasController;
import com.cas735.lab6.biometricsrv.adapters.rest.WorkoutHateoasController;
import com.cas735.lab6.biometricsrv.business.entities.Workout;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class WorkoutModelAssembler
        implements RepresentationModelAssembler<Workout, EntityModel<Workout>> {

    @Override
    public EntityModel<Workout> toModel(Workout entity) {
        Link self = linkTo(methodOn(WorkoutHateoasController.class)
                .findOneById(entity.getId())).withSelfRel();
        Link root = linkTo(methodOn(WorkoutHateoasController.class)
                .findAll()).withRel("workouts");
        Link endWorkout = linkTo(methodOn(WorkoutHateoasController.class)
                .finish(null, entity.getId())).withRel("workouts");
        Link heartratesByWorkout = linkTo(methodOn(HeartrateHateoasController.class)
                .findByWorkoutId(entity.getId())).withRel("heartrates");
        Link heartratesByUsername = linkTo(methodOn(HeartrateHateoasController.class)
                .findByUsername(entity.getUsername())).withRel("heartrates");
        return EntityModel.of(entity, self, root, endWorkout, heartratesByWorkout, heartratesByUsername);
    }
}
