package com.cas735.lab6.gatlingtests;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import net.datafaker.Faker;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;


/**
 * This sample is based on our official tutorials:
 * <ul>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/quickstart">Gatling quickstart tutorial</a>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/advanced">Gatling advanced tutorial</a>
 * </ul>
 */
@Slf4j
public class WorkoutSimulation extends Simulation {

    private int REPETITIONS = 10;

    Faker faker = new Faker();

    ChainBuilder getHeartrates =
        pause(10).repeat(REPETITIONS).on(
        exec(session -> session.set("workoutId", ThreadLocalRandom.current().nextInt(1000) + 1)).
        exec(http("Get Workout Heartrates")
                .get("/v1/workouts/#{workoutId}/heartrates")
                .check(
                    status().is(200)
                )).pause(1));

    ChainBuilder getWorkouts =
        pause(10).repeat(REPETITIONS).on(
        exec(session -> session.set("workoutId", ThreadLocalRandom.current().nextInt(1000) + 1)).
        exec(http("Get Workout")
                .get("/v1/workouts/#{workoutId}")
                .check(
                    status().is(200)
                )).pause(1));

    // createWorkout simulation
    ChainBuilder createWorkout =
    // let's try at max 2 times
    tryMax(2)
        .on(
            exec(session ->
                {
                    String username = faker.stargate().characters();

                    Session newSession = session.set("username", username).set("now", currentDateTimeString());

                    return newSession;
                }
            )
        ).exec(
            http("Create Workout")
            .post("/v1/workouts")
            .body(StringBody("""
                { "username": "#{username}", "startDateTime": "#{now}" }"""))
            .asJson()
            .check(
                status().is(200)
            )
            .check(jsonPath("$..id").saveAs("workoutId"))
        )
            .repeat(REPETITIONS).on(
                exec(session -> session.set("randomNumber", ThreadLocalRandom.current().nextInt(140) + 60)).
                exec(session -> session.set("now", currentDateTimeString())).
                exec(
                    http("Create Heartrate")
                    .post("/v1/workouts/#{workoutId}/heartrates")
                    .body(StringBody("""
                        { "workoutId": "#{workoutId}", "dateTime": "#{now}", "heartrate": #{randomNumber} }"""))
                    .asJson()
                    .check(
                        status().is(200)
                    ))
            ).pause(1)
        // if the chain didn't finally succeed, have the user exit the whole scenario
        .exitHereIfFailed();

    HttpProtocolBuilder httpProtocol =
        http.baseUrl("http://localhost:9090")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0"
            );

    ScenarioBuilder users = scenario("Users").exec(createWorkout, getHeartrates, getWorkouts);

    {
        setUp(
            users.injectOpen(rampUsers(1000).during(10))
        ).protocols(httpProtocol);
    }

    private String currentDateTimeString() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTime = now.format(formatter);

        return formattedDateTime;
    }
}