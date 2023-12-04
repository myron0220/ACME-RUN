package com.cas735.finalproject.gpsmonitorsrv.business;

import com.cas735.finalproject.gpsmonitorsrv.dto.CreateWorkoutRequest;
import com.cas735.finalproject.gpsmonitorsrv.ports.GPSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;

import com.cas735.finalproject.gpsmonitorsrv.business.entities.Workout;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import javax.annotation.PreDestroy;

@Service
@Slf4j
public class GPSMonitorManager {
    GPSService GPSService;
    Workout workout = null;
    Integer lastHeartrate = 72;
    private static final Integer MIN_HR = 60;  // minimum heartrate
    private static final Integer MAX_HR = 200; // maximum heartrate
    private static final Integer INTERVAL = 5; // maximum the heartrate can go up or down by each second

    // this module simulates a heartrate monitor by registering a new workout and then sending a bunch of heartrates, one per second, until it's turned off
    public GPSMonitorManager(GPSService GPSService) {
        this.GPSService = GPSService;

        // register a new workout
        while (this.workout == null) {
            this.workout = GPSService.createWorkout("Chris", LocalDateTime.now());
            // sleep for 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Workout createWorkout(String username, LocalDateTime startDateTime) {
        CreateWorkoutRequest createWorkoutRequest = new CreateWorkoutRequest(username, startDateTime);
        Workout workoutResponse;
        try {
            WebClient webClient = buildClient();
            workoutResponse =
                    webClient.post()
                            .uri(ENDPOINT)
                            .body(BodyInserters.fromValue(createWorkoutRequest))
                            .retrieve().bodyToMono(Workout.class).block();
            return workoutResponse;
        } catch (IllegalStateException ex) {
            log.error("No biometric service available!");
            return null;
        } catch (WebClientException ex) {
            log.error("Communication Error while sending workout request");
            log.error(ex.toString());
            return null;
        }
    }

    @PreDestroy
    public void destroy() {
        GPSService.endWorkout(this.workout.getId(), LocalDateTime.now());
    }

    @Scheduled(fixedRate=1000) // https://stackoverflow.com/a/36542208
    public void sendData() {
            // if we don't have a workout yet, don't send any data
            if (this.workout == null)
                return;

            Integer heartrate = generateNextHeartrate();

            log.info("Sending heartrate: " + heartrate + "bpm");
            log.info("Sending workout: " + workout.getId());
            GPSService.sendHeartrate(workout.getId(), LocalDateTime.now(), heartrate);
    }

    // Generates the next random heartrate in a sequence, from 60 to 200. 
    // The heartrate has a higher probability of going up when closer to 60, and vice versa
    private Integer generateNextHeartrate() {
        Random random = new Random();
        double increaseProb = (double) (MAX_HR - lastHeartrate) / (double) (MAX_HR - MIN_HR); 
        Integer increase = (int) Math.round((double) INTERVAL * increaseProb);
        Integer decrease = INTERVAL - increase;
        Integer nextHeartrate = lastHeartrate + (random.nextInt(-decrease, increase));
        //log.info("IP: " + increaseProb + ", I: " + increase + ", D: " + decrease + ", HR: " + nextHeartrate);

        this.lastHeartrate = nextHeartrate;

        return nextHeartrate > 200 ? 200 
                : nextHeartrate < 60 ? 60 
                    : nextHeartrate;
    }
}
