package com.cas735.finalproject.biometricsrv.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cas735.finalproject.biometricsrv.business.HeartrateManager;
import com.cas735.finalproject.biometricsrv.business.entities.Heartrate;
import com.cas735.finalproject.biometricsrv.dto.DeletionRequest;
import com.cas735.finalproject.biometricsrv.ports.HeartrateFinder;
import com.cas735.finalproject.biometricsrv.ports.WorkoutFinder;

import java.util.concurrent.ThreadLocalRandom;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class HeartrateController {

    private static final String ENDPOINT = "/heartrates";

    @Autowired
    private HeartrateFinder heartrateFinder;
    @Autowired
    private HeartrateManager heartrateManager;
    @Autowired
    private WorkoutFinder workoutFinder;


    @GetMapping(ENDPOINT)
    public List<Heartrate> findAll() {
        return heartrateFinder.findAll();
    }

    @GetMapping(ENDPOINT+"/{id}")
    public Heartrate findOneById(@PathVariable Long id) {
        return heartrateFinder.findOneById(id);
    }

    @GetMapping("/users/{username}/heartrates")
    public List<Heartrate> findHeartratesByUsername(@PathVariable String username) {
        return heartrateFinder.findByUsername(username);
    }

    @GetMapping("/workouts/{workoutId}/heartrates")
    public List<Heartrate> findHeartratesByWorkoutId(@PathVariable Long workoutId) {
        boolean fixPerformanceProblem = false;

        if (!fixPerformanceProblem) {
            try {
                int min = 0;
                int max = 1500;
                long sleepTime = ThreadLocalRandom.current().nextLong(min, max);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                log.info("Exception thrown");
            }
        }
        return heartrateFinder.findByWorkoutId(workoutId);
    }

    @PostMapping("/workouts/{workoutId}/heartrates")
    public Heartrate createHeartrate(@RequestBody Heartrate heartrateRequest) {
        return heartrateManager.create(heartrateRequest);
    }

    @DeleteMapping(ENDPOINT+"/{id}")
    public void delete(@RequestBody DeletionRequest del, @PathVariable Long id) {
        if ( !del.getId().equals(id) )
            throw new IllegalArgumentException("Identifier Mismatch");

        heartrateManager.delete(id);
    }

    @DeleteMapping("/workouts/{workoutId}/heartrates")
    public void deleteWorkoutHeartrates(@RequestBody DeletionRequest del, @PathVariable Long workoutId) {
        if ( !del.getId().equals(workoutId) )
            throw new IllegalArgumentException("Identifier Mismatch");

        heartrateManager.deleteByWorkoutId(workoutId);
    }
}
