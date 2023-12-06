package com.cas735.finalproject.heartratemonitorsrv.adapters;

import com.cas735.finalproject.heartratemonitorsrv.business.entities.*;
import com.cas735.finalproject.heartratemonitorsrv.dto.TrailAllocationRequest;
import com.cas735.finalproject.heartratemonitorsrv.dto.UpdateUserScoreRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.cas735.finalproject.heartratemonitorsrv.RabbitConfiguration;
import com.cas735.finalproject.heartratemonitorsrv.dto.CreateWorkoutRequest;
import com.cas735.finalproject.heartratemonitorsrv.dto.EndWorkoutRequest;
import com.cas735.finalproject.heartratemonitorsrv.ports.HeartrateService;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
public class HeartrateClient implements HeartrateService {
    private final RabbitTemplate rabbitTemplate;
    private final EurekaClient registry; //Eureka client to look up services

    private static final String WORKOUT_ENDPOINT = "v1/workouts";

    private static final String TRAIL_ENDPOINT = "v1/trail/allocate";

    private static final String USERSCORE_ENDPOINT = "v1/user";

    private static final String WORKOUT_APP_NAME = "game-center-service";
    private static final String TRAIL_APP_NAME = "trail-provider-service";

    private static final String USERSCORE_APP_NAME = "point-service";

    @Autowired
    public HeartrateClient(RabbitTemplate rabbitTemplate, EurekaClient registry) {
        this.rabbitTemplate = rabbitTemplate;
        this.registry = registry;
    }

    private WebClient buildWorkoutClient() {
        String url = locateExternalWorkoutService();
        log.info("** Using instance: " + url);
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private WebClient buildTrailClient() {
        String url = locateExternalTrailService();
        log.info("** Using instance: " + url);
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private WebClient buildUserScoreClient() {
        String url = locateExternalUserScoreService();
        log.info("** Using instance: " + url);
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private String locateExternalWorkoutService() {
        Application candidates = registry.getApplication(WORKOUT_APP_NAME);
        if (Objects.isNull(candidates)) { // no email service in the registry
            throw new IllegalStateException();
        }
        Random rand = new Random();
        InstanceInfo infos = // Randomly picking one email service among candidates
                candidates.getInstances().get(rand.nextInt(candidates.size()));
        return "http://"+infos.getIPAddr()+":"+infos.getPort();
    }

    private String locateExternalTrailService() {
        Application candidates = registry.getApplication(TRAIL_APP_NAME);
        if (Objects.isNull(candidates)) {
            throw new IllegalStateException();
        }
        Random rand = new Random();
        InstanceInfo infos = // Randomly picking one email service among candidates
                candidates.getInstances().get(rand.nextInt(candidates.size()));
        return "http://"+infos.getIPAddr()+":"+infos.getPort();
    }

    private String locateExternalUserScoreService() {
        Application candidates = registry.getApplication(USERSCORE_APP_NAME);
        if (Objects.isNull(candidates)) { // no email service in the registry
            throw new IllegalStateException();
        }
        Random rand = new Random();
        InstanceInfo infos = // Randomly picking one email service among candidates
                candidates.getInstances().get(rand.nextInt(candidates.size()));
        return "http://"+infos.getIPAddr()+":"+infos.getPort();
    }


    @Override
    public void sendHeartrate(Long workoutId, LocalDateTime dateTime, Integer heartrate, Double latitude, Double longitude) {
        Heartrate newHeartrate = new Heartrate(workoutId, dateTime, heartrate, latitude, longitude);
        
        rabbitTemplate.convertAndSend(RabbitConfiguration.HEARTRATE_EXCHANGE_NAME,
            RabbitConfiguration.HEARTRATE_ROUTING_KEY, newHeartrate);
    }

    @Override
    public Workout createWorkout(String username, LocalDateTime startDateTime) {
        CreateWorkoutRequest createWorkoutRequest = new CreateWorkoutRequest(username, startDateTime);
        Workout workoutResponse;
        try {
            WebClient webClient = buildWorkoutClient();
            workoutResponse = 
                webClient.post()
                    .uri(WORKOUT_ENDPOINT)
                    .body(BodyInserters.fromValue(createWorkoutRequest))
                    .retrieve().bodyToMono(Workout.class).block();
            return workoutResponse;
        } catch (IllegalStateException ex) {
            log.error("No game centre service available!");
            return null;
        } catch (WebClientException ex) {
            log.error("Communication Error while sending trail request");
            log.error(ex.toString());
            return null;
        }
    }

    @Override
    public void endWorkout(Long workoutId, LocalDateTime endTime) {
        EndWorkoutRequest endWorkoutRequest = new EndWorkoutRequest(workoutId, endTime);
        try {
            WebClient webClient = buildWorkoutClient();
            webClient.patch()
                    .uri(WORKOUT_ENDPOINT+"/"+endWorkoutRequest.getId().toString()+"/endtime")
                    .body(BodyInserters.fromValue(endWorkoutRequest))
                    .retrieve().bodyToMono(String.class).block();
        } catch (IllegalStateException ex) {
            log.error("No game centre service available!");
        } catch (WebClientException ex) {
            log.error("Communication Error while sending workout request");
            log.error(ex.toString());
        }
    }

    @Override
    public void updateUserScore(String username, int newPoint) {
        UpdateUserScoreRequest updateUserScoreRequest = new UpdateUserScoreRequest(username, LocalDateTime.now());
        try {
            WebClient webClient = buildUserScoreClient();
            webClient.post()
                    .uri(USERSCORE_ENDPOINT + "?username=" + username + "&newPoints=" + newPoint)
                    .retrieve().bodyToMono(String.class).block();
        } catch (IllegalStateException ex) {
            log.error("No point service available!");
        } catch (WebClientException ex) {
            log.error("Communication Error while sending update user score request");
            log.error(ex.toString());
        }
    }

    @Override
    public User findUserScore(String username) {
        UpdateUserScoreRequest updateUserScoreRequest = new UpdateUserScoreRequest(username, LocalDateTime.now());
        try {
            WebClient webClient = buildUserScoreClient();
            User userResponse = webClient.get()
                    .uri(USERSCORE_ENDPOINT + "/" + username)
                    .retrieve().bodyToMono(User.class).block();
            return  userResponse;
        } catch (IllegalStateException ex) {
            log.error("No point service available!");
            return null;
        } catch (WebClientException ex) {
            log.error("Communication Error while sending update user score request");
            log.error(ex.toString());
            return null;
        }
    }

    @Override
    public Trail requestTrailAllocation(String username, Location location) {
        TrailAllocationRequest trailAllocationRequest = new TrailAllocationRequest(username, LocalDateTime.now());
        Trail trailResponse;
        try {
            WebClient webClient = buildTrailClient();
            trailResponse =
                    webClient.get()
                            .uri(TRAIL_ENDPOINT)
                            .retrieve().bodyToMono(Trail.class).block();
            return trailResponse;
        } catch (IllegalStateException ex) {
            log.error("No trail allocation service available!");
            return null;
        } catch (WebClientException ex) {
            log.error("Communication Error while sending trail request");
            log.error(ex.toString());
            return null;
        }
    }

}
