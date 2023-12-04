package com.cas735.finalproject.biometricsrv.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.*;

import com.cas735.finalproject.biometricsrv.business.entities.Workout;
import com.cas735.finalproject.biometricsrv.dto.CreateWorkoutRequest;
import com.cas735.finalproject.biometricsrv.dto.EndWorkoutRequest;
import com.cas735.finalproject.biometricsrv.adapters.assemblers.WorkoutModelAssembler;
import com.cas735.finalproject.biometricsrv.ports.WorkoutHateoas;
import com.cas735.finalproject.biometricsrv.ports.WorkoutFinder;
import com.cas735.finalproject.biometricsrv.business.WorkoutManager;
import com.cas735.finalproject.biometricsrv.dto.DeletionRequest;

import java.util.List;


@RestController
@RequestMapping(value = "/v2", produces = "application/json")
public class WorkoutHateoasController implements WorkoutHateoas {

    private static final String ENDPOINT = "/workouts";
    @Autowired
    private WorkoutManager manager;
    @Autowired
    private WorkoutFinder finder;
    @Autowired
    private WorkoutModelAssembler assembler;

    @Override
    @GetMapping(ENDPOINT)
    public CollectionModel<EntityModel<Workout>> findAll() {
        List<EntityModel<Workout>> workouts = finder.findAll()
                .stream().map(assembler::toModel).toList();

        return CollectionModel.of(workouts,
                linkTo(methodOn(WorkoutHateoasController.class).findAll()).withSelfRel()
                );
    }

    @Override
    @GetMapping(ENDPOINT+"/{id}")
    public EntityModel<Workout> findOneById(@PathVariable Long id) {
        Workout aWorkout = finder.findOneById(id);
        return assembler.toModel(aWorkout);
    }

    @Override
    @GetMapping("/users/{username}/workouts")
    public CollectionModel<EntityModel<Workout>> findByUsername(@PathVariable String username) {
        List<EntityModel<Workout>> workouts = finder.findByUsername(username)
            .stream().map(assembler::toModel).toList();

            return CollectionModel.of(workouts,
                    linkTo(methodOn(WorkoutHateoasController.class).findAll()).withSelfRel()
                    );
    }

    @Override
    @PostMapping(ENDPOINT)
    public EntityModel<Workout> create(@RequestBody CreateWorkoutRequest workoutRequest) {
        Workout workout = manager.create(workoutRequest.getUsername(), workoutRequest.getStartDateTime());
        return assembler.toModel(workout);
    }

    @Override
    @PatchMapping(ENDPOINT+"/{id}/endtime")
    public EntityModel<Workout> finish(@RequestBody EndWorkoutRequest req, @PathVariable Long id) {
        if ( !req.getId().equals(id) )
            throw new IllegalArgumentException("Identifier Mismatch");
        manager.endWorkout(id, req.getEndDateTime());

        return assembler.toModel(finder.findOneById(id));
    }

    @Override
    @DeleteMapping(ENDPOINT+"/{id}")
    public void delete(@RequestBody DeletionRequest del, @PathVariable Long id) {
        if ( !del.getId().equals(id) )
            throw new IllegalArgumentException("Identifier Mismatch");
        manager.delete(id);
    }

}
