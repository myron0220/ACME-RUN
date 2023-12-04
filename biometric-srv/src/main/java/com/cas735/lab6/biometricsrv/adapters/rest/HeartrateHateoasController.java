package com.cas735.lab6.biometricsrv.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.*;

import com.cas735.lab6.biometricsrv.business.entities.Heartrate;
import com.cas735.lab6.biometricsrv.adapters.assemblers.HeartrateModelAssembler;
import com.cas735.lab6.biometricsrv.ports.HeartrateHateoas;
import com.cas735.lab6.biometricsrv.ports.HeartrateFinder;
import com.cas735.lab6.biometricsrv.ports.WorkoutFinder;
import com.cas735.lab6.biometricsrv.business.HeartrateManager;
import com.cas735.lab6.biometricsrv.dto.DeletionRequest;

import java.util.List;


@RestController
@RequestMapping(value = "/v2", produces = "application/json")
public class HeartrateHateoasController implements HeartrateHateoas {

    private static final String ENDPOINT = "/heartrates";
    @Autowired
    private HeartrateFinder heartrateFinder;
    @Autowired
    private HeartrateManager heartrateManager;
    @Autowired
    private WorkoutFinder workoutFinder;
    @Autowired
    private HeartrateModelAssembler assembler;

    @Override
    @GetMapping(ENDPOINT)
    public CollectionModel<EntityModel<Heartrate>> findAll() {
        List<EntityModel<Heartrate>> heartrates = heartrateFinder.findAll()
                .stream().map(assembler::toModel).toList();

        return CollectionModel.of(heartrates,
                linkTo(methodOn(HeartrateHateoasController.class).findAll()).withSelfRel()
                );
    }

    @Override
    @GetMapping(ENDPOINT+"/{id}")
    public EntityModel<Heartrate> findOneById(@PathVariable Long id) {
        Heartrate aHeartrate = heartrateFinder.findOneById(id);
        return assembler.toModel(aHeartrate);
    }

    @Override
    @GetMapping("/users/{username}/heartrates")
    public CollectionModel<EntityModel<Heartrate>> findByUsername(@PathVariable String username) {
        List<EntityModel<Heartrate>> heartrates = heartrateFinder.findByUsername(username)
            .stream().map(assembler::toModel).toList();

            return CollectionModel.of(heartrates,
                    linkTo(methodOn(HeartrateHateoasController.class).findAll()).withSelfRel(),
                    linkTo(methodOn(HeartrateHateoasController.class).findByUsername(username)).withSelfRel()
                    );
    }

    @Override
    @GetMapping("/workouts/{workoutId}/heartrates")
    public CollectionModel<EntityModel<Heartrate>> findByWorkoutId(@PathVariable Long workoutId) {
        List<EntityModel<Heartrate>> heartrates = heartrateFinder.findByWorkoutId(workoutId)
            .stream().map(assembler::toModel).toList();

        return CollectionModel.of(heartrates,
                linkTo(methodOn(HeartrateHateoasController.class).findAll()).withSelfRel(),
                linkTo(methodOn(HeartrateHateoasController.class).findByWorkoutId(workoutId)).withSelfRel()
                );
    }

    @Override
    @PostMapping("/workouts/{workoutId}/heartrates")
    public EntityModel<Heartrate> create(@RequestBody Heartrate heartrateRequest) {
        Heartrate heartrate = heartrateManager.create(heartrateRequest);
        return assembler.toModel(heartrate);
    }

    @Override
    @DeleteMapping(ENDPOINT+"/{id}")
    public void delete(@RequestBody DeletionRequest del, @PathVariable Long id) {
        if ( !del.getId().equals(id) )
            throw new IllegalArgumentException("Identifier Mismatch");
        heartrateManager.delete(id);
    }

    @DeleteMapping("/workouts/{workoutId}/heartrates")
    public void deleteByWorkoutId(@RequestBody DeletionRequest del, @PathVariable Long workoutId) {
        if ( !del.getId().equals(workoutId) )
            throw new IllegalArgumentException("Identifier Mismatch");

        heartrateManager.deleteByWorkoutId(workoutId);
    }
}
