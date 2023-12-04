package com.cas735.finalproject.gpsmonitorsrv.adapters;

import com.cas735.finalproject.gpsmonitorsrv.business.entities.GPS;
import com.cas735.finalproject.gpsmonitorsrv.dto.CreateWorkoutRequest;
import com.cas735.finalproject.gpsmonitorsrv.dto.EndWorkoutRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.cas735.finalproject.gpsmonitorsrv.business.entities.Workout;
import com.cas735.finalproject.gpsmonitorsrv.RabbitConfiguration;
import com.cas735.finalproject.gpsmonitorsrv.business.entities.Heartrate;
import com.cas735.finalproject.gpsmonitorsrv.ports.GPSService;


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
public class GPSClient implements GPSService {
    private final RabbitTemplate rabbitTemplate;

    //Eureka client to look up services
    private final EurekaClient registry;
    private static final String APP_NAME = "game-center-service";

    @Autowired
    public GPSClient(RabbitTemplate rabbitTemplate, EurekaClient registry) {
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
    public void sendGPS(Double latitude, Double longitude) {
        GPS newHeartrate = new GPS((long) 1, LocalDateTime.now(), latitude, longitude);
        
        rabbitTemplate.convertAndSend(RabbitConfiguration.GPS_EXCHANGE_NAME,
            RabbitConfiguration.GPS_ROUTING_KEY, newHeartrate);
    }
}
