package com.cas735.finalproject.heartratemonitorsrv.business;

import com.cas735.finalproject.heartratemonitorsrv.business.entities.Location;
import com.cas735.finalproject.heartratemonitorsrv.business.entities.Trail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Random;

import java.time.LocalDateTime;
import java.util.Scanner;

import org.springframework.scheduling.annotation.Scheduled;

import com.cas735.finalproject.heartratemonitorsrv.business.entities.Workout;
import com.cas735.finalproject.heartratemonitorsrv.ports.HeartrateService;

import javax.annotation.PreDestroy;

@Service
@Slf4j
public class HeartrateMonitorManager {
    HeartrateService heartrateService;
    Workout workout = null;

    Trail allocatedTrail = null;

    String userName = null;
    Integer lastHeartrate = 72;
    private static final Integer MIN_HR = 60;  // minimum heartrate
    private static final Integer MAX_HR = 200; // maximum heartrate
    private static final Integer INTERVAL = 5; // maximum the heartrate can go up or down by each second

    private double currentLatitude = 37.7749; // starting latitude

    private double currentLongitude = -122.4194; // starting longitude

    private static final double MOVEMENT_SPEED = 0.0001; // movement speed in degrees

    // this module simulates a heartrate monitor by registering a new workout and then sending a bunch of heartrates, one per second, until it's turned off
    public HeartrateMonitorManager(HeartrateService heartrateService) {
        this.heartrateService = heartrateService;
        Scanner scanner = new Scanner(System.in);
        log.info("------------------- Welcome to the ACMERUN APP -----------------");
        log.info("Please enter your username: ");
        this.userName = scanner.next();

        // register a new workout
        while (this.workout == null) {
            this.workout = heartrateService.createWorkout(userName, LocalDateTime.now());
            // sleep for 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("------------------------------------");
        log.info("Sending workout start request...");
        log.info("Receive workout response. " + workout.toString());
        log.info("------------------------------------");

        // request a new trail allocation
        while (this.allocatedTrail == null) {
            this.allocatedTrail = heartrateService.requestTrailAllocation(userName, new Location(32, -155));
            // sleep for 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("------------------------------------");
        log.info("Sending trail allocation request...");
        log.info("Receive trail response. " + allocatedTrail.toString());
        log.info("------------------------------------");
    }

    @PreDestroy
    public void destroy() {
        log.info("------------------------------------");
        log.info("Sending workout end request...");
        heartrateService.endWorkout(this.workout.getId(), LocalDateTime.now());
        log.info("Ended. May you enjoyed this training.");
        log.info("------------------------------------");
    }

    @Scheduled(fixedRate=1000) // https://stackoverflow.com/a/36542208
    public void sendData() {
        // if we don't have a workout yet, don't send any data
        if (this.workout == null)
            return;

        Integer heartrate = generateNextHeartrate();
        double[] gps = generateNextGPS();
        heartrateService.sendHeartrate(workout.getId(),LocalDateTime.now(), heartrate, gps[0], gps[1]);

        log.info("------------------- curren state ---------------------");
        log.info("user: " + this.userName);
        log.info("workout: " + workout.getId());
        log.info("heartrate: " + heartrate + "bpm");
        log.info("latitude: " + gps[0]);
        log.info("longitude: " + gps[1]);
        log.info("-------------------------------------------------------");
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

    private double[] generateNextGPS() {
        currentLatitude += MOVEMENT_SPEED;
        currentLongitude += MOVEMENT_SPEED;

        return new double[]{currentLatitude, currentLongitude};
    }
}
