package com.cas735.lab6.heartratemonitorsrv.adapters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.cas735.lab6.heartratemonitorsrv.business.entities.Workout;
import com.cas735.lab6.heartratemonitorsrv.RabbitConfiguration;
import com.cas735.lab6.heartratemonitorsrv.business.entities.Heartrate;
import com.cas735.lab6.heartratemonitorsrv.dto.CreateWorkoutRequest;
import com.cas735.lab6.heartratemonitorsrv.dto.EndWorkoutRequest;
import com.cas735.lab6.heartratemonitorsrv.ports.BiometricService;


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
public class BiometricClient implements BiometricService {
    private final RabbitTemplate rabbitTemplate;
    private static final String ENDPOINT = "v1/workouts";

    //Eureka client to look up services
    private final EurekaClient registry;
    private static final String APP_NAME = "BIOMETRIC-SERVICE";

    @Autowired
    public BiometricClient(RabbitTemplate rabbitTemplate, EurekaClient registry) {
        this.rabbitTemplate = rabbitTemplate;
        this.registry = registry;
    }

    private WebClient buildClient() {
        String url = locateExternalService();
        log.info("** Using instance: " + url);
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private String locateExternalService() {
        Application candidates = registry.getApplication(APP_NAME);
        if (Objects.isNull(candidates)) { // no email service in the registry
            throw new IllegalStateException();
        }
        Random rand = new Random();
        InstanceInfo infos = // Randomly picking one email service among candidates
                candidates.getInstances().get(rand.nextInt(candidates.size()));
        return "http://"+infos.getIPAddr()+":"+infos.getPort();
    }


    @Override
    public void sendHeartrate(Long workoutId, LocalDateTime dateTime, Integer heartrate) {
        Heartrate newHeartrate = new Heartrate(workoutId, dateTime, heartrate);
        
        rabbitTemplate.convertAndSend(RabbitConfiguration.EXCHANGE_NAME,
            RabbitConfiguration.ROUTING_KEY, newHeartrate);
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

    @Override
    public void endWorkout(Long workoutId, LocalDateTime endTime) {
        EndWorkoutRequest endWorkoutRequest = new EndWorkoutRequest(workoutId, endTime);
        try {
            WebClient webClient = buildClient();
            webClient.patch()
                    .uri(ENDPOINT+"/"+endWorkoutRequest.getId().toString()+"/endtime")
                    .body(BodyInserters.fromValue(endWorkoutRequest))
                    .retrieve().bodyToMono(String.class).block();
        } catch (IllegalStateException ex) {
            log.error("No biometic service available!");
        } catch (WebClientException ex) {
            log.error("Communication Error while sending workout request");
            log.error(ex.toString());
        }
    }

}
