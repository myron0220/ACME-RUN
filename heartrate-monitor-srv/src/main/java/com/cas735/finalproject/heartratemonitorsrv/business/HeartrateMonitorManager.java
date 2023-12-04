package com.cas735.finalproject.heartratemonitorsrv.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;

import com.cas735.finalproject.heartratemonitorsrv.business.entities.Workout;
import com.cas735.finalproject.heartratemonitorsrv.ports.BiometricService;

import javax.annotation.PreDestroy;

@Service
@Slf4j
public class HeartrateMonitorManager {
    BiometricService biometricService;
    Workout workout = null;
    Integer lastHeartrate = 72;
    private static final Integer MIN_HR = 60;  // minimum heartrate
    private static final Integer MAX_HR = 200; // maximum heartrate
    private static final Integer INTERVAL = 5; // maximum the heartrate can go up or down by each second

    // this module simulates a heartrate monitor by registering a new workout and then sending a bunch of heartrates, one per second, until it's turned off
    public HeartrateMonitorManager(BiometricService biometricService) {
        this.biometricService = biometricService;

        // register a new workout
        while (this.workout == null) {
            this.workout = biometricService.createWorkout("Chris", LocalDateTime.now());
            // sleep for 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @PreDestroy
    public void destroy() {
        biometricService.endWorkout(this.workout.getId(), LocalDateTime.now());
    }

    @Scheduled(fixedRate=1000) // https://stackoverflow.com/a/36542208
    public void sendData() {
            // if we don't have a workout yet, don't send any data
            if (this.workout == null)
                return;

            Integer heartrate = generateNextHeartrate();

            log.info("Sending heartrate: " + heartrate + "bpm");
            log.info("Sending workout: " + workout.getId());
            biometricService.sendHeartrate(workout.getId(), LocalDateTime.now(), heartrate);
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
