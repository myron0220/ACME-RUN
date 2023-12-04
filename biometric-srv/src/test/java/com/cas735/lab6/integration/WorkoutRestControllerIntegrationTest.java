package com.cas735.lab6.entities;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.context.TestPropertySource;

import com.cas735.lab6.biometricsrv.business.entities.Heartrate;
import com.cas735.lab6.biometricsrv.business.entities.Workout;
import com.cas735.lab6.biometricsrv.BiometricApplication;
import com.cas735.lab6.biometricsrv.business.WorkoutRegistry;
import com.cas735.lab6.biometricsrv.ports.WorkoutRepository;
import com.cas735.lab6.biometricsrv.ports.WorkoutFinder;
import com.cas735.lab6.biometricsrv.ports.WorkoutRepository;

// add annotations here
public class WorkoutRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WorkoutRepository repository;

    // write test cases here
}
