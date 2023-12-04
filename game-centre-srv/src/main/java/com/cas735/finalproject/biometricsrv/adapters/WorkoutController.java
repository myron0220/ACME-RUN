package com.cas735.finalproject.biometricsrv.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cas735.finalproject.biometricsrv.business.WorkoutManager;
import com.cas735.finalproject.biometricsrv.business.entities.Workout;
import com.cas735.finalproject.biometricsrv.dto.CreateWorkoutRequest;
import com.cas735.finalproject.biometricsrv.dto.DeletionRequest;
import com.cas735.finalproject.biometricsrv.dto.EndWorkoutRequest;
import com.cas735.finalproject.biometricsrv.ports.HeartrateFinder;
import com.cas735.finalproject.biometricsrv.ports.WorkoutFinder;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class WorkoutController {

    private static final String ENDPOINT = "/workouts";

    @Autowired
    private WorkoutManager manager;

    @Autowired
    private WorkoutFinder finder;

    @Autowired
    private HeartrateFinder heartrateFinder;

    @GetMapping(ENDPOINT)
    public List<Workout> findAll() {
        return finder.findAll();
    }

    @GetMapping(ENDPOINT+"/{id}")
    public Workout findOneById(@PathVariable Long id) {
        if (Math.random() < 0.01)
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "entity not found"
            );

        return finder.findOneById(id);
    }

    @GetMapping("/users/{username}"+ENDPOINT)
    public List<Workout> findByUsername(@PathVariable String username) {
        return finder.findByUsername(username);
    }

    @PostMapping(ENDPOINT)
    public Workout create(@RequestBody CreateWorkoutRequest req) {
        Workout newWorkout = manager.create(req.getUsername(), req.getStartDateTime());
        return newWorkout;
    }

    @PatchMapping(ENDPOINT+"/{id}/endtime")
    public Workout finish(@RequestBody EndWorkoutRequest req, @PathVariable Long id) {
        if ( !req.getId().equals(id) )
            throw new IllegalArgumentException("Identifier Mismatch");
        manager.endWorkout(id, req.getEndDateTime());

        return finder.findOneById(id);
    }

    @DeleteMapping(ENDPOINT+"/{id}")
    public void delete(@RequestBody DeletionRequest del, @PathVariable Long id) {
        if ( !del.getId().equals(id) )
            throw new IllegalArgumentException("Identifier Mismatch");
        manager.delete(id);
    }
}
