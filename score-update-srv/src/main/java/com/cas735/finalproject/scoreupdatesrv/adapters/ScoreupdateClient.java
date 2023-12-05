package com.cas735.finalproject.scoreupdatesrv.adapters;

import com.cas735.finalproject.scoreupdatesrv.business.entities.Score;
import com.cas735.finalproject.scoreupdatesrv.dto.CreateScoreRequest;
import com.cas735.finalproject.scoreupdatesrv.dto.DeleteScoreRequest;
import com.cas735.finalproject.scoreupdatesrv.dto.UpdateScoreRequest;
import com.cas735.finalproject.scoreupdatesrv.ports.ScoreupdateService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


import com.netflix.discovery.EurekaClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
public class ScoreupdateClient implements ScoreupdateService {
    private final RabbitTemplate rabbitTemplate;

    private static final String SCORE_ENDPOINT = "v1/score";

    //Eureka client to look up services
    private final EurekaClient registry;
    private static final String SCORE_APP_NAME = "score-update-service";

    @Autowired
    public ScoreupdateClient(RabbitTemplate rabbitTemplate, EurekaClient registry) {
        this.rabbitTemplate = rabbitTemplate;
        this.registry = registry;
    }

    private WebClient buildScoreUpdateClient() {
        String url = locateExternalScoreUpdateService();
        log.info("** Using instance: " + url);
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private String locateExternalScoreUpdateService() {
        Application candidates = registry.getApplication(SCORE_APP_NAME);
        if (Objects.isNull(candidates)) { // no email service in the registry
            throw new IllegalStateException();
        }
        Random rand = new Random();
        InstanceInfo infos = // Randomly picking one email service among candidates
                candidates.getInstances().get(rand.nextInt(candidates.size()));
        return "http://" + infos.getIPAddr() + ":" + infos.getPort();
    }

    @Override
    public void deleteScore(Long personId) {
        DeleteScoreRequest deleteScoreRequest = new DeleteScoreRequest(personId);
        Score scoreResponse;
        try {
            WebClient webClient = buildScoreUpdateClient();
            scoreResponse = webClient.patch()
                    .uri(SCORE_ENDPOINT + "/" + deleteScoreRequest.getId().toString() + "/delete")
                    .body(BodyInserters.fromValue(deleteScoreRequest))
                    .retrieve().bodyToMono(Score.class).block();
        } catch (IllegalStateException ex) {
            log.error("No score service available!");
        } catch (WebClientException ex) {
            log.error("Communication Error while sending workout request");
            log.error(ex.toString());
        }
    }

    @Override
    public Score createScore(String username) {
        Integer i = new Random().nextInt();
        Long rand = Long.parseLong(String.valueOf(i));
        CreateScoreRequest createScoreRequest = new CreateScoreRequest(rand, username, 0);
        Score scoreResponse;
        try {
            WebClient webClient = buildScoreUpdateClient();
            scoreResponse = webClient.post()
                    .uri(SCORE_ENDPOINT + "/" + createScoreRequest.getId().toString() + "/create")
                    .body(BodyInserters.fromValue(createScoreRequest))
                    .retrieve().bodyToMono(Score.class).block();
            return scoreResponse;
        } catch (IllegalStateException ex) {
            log.error("No score service available!");
            return null;
        } catch (WebClientException ex) {
            log.error("Communication Error while sending workout request");
            log.error(ex.toString());
            return null;
        }
    }

    @Override
    public void updateScore(Long personId) {
        UpdateScoreRequest updateScoreRequest = new UpdateScoreRequest(personId);
        Score scoreResponse;
        try {
            WebClient webClient = buildScoreUpdateClient();
            scoreResponse = webClient.post()
                    .uri(SCORE_ENDPOINT + "/" + updateScoreRequest.getId().toString() + "/update")
                    .body(BodyInserters.fromValue(updateScoreRequest))
                    .retrieve().bodyToMono(Score.class).block();
        } catch (IllegalStateException ex) {
            log.error("No score service available!");
        } catch (WebClientException ex) {
            log.error("Communication Error while sending workout request");
            log.error(ex.toString());
        }

    }


}
