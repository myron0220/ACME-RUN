package com.cas735.finalproject.trailprovidersrv.adapters;

import com.cas735.finalproject.trailprovidersrv.business.entities.Location;
import com.cas735.finalproject.trailprovidersrv.business.entities.Trail;
import com.cas735.finalproject.trailprovidersrv.ports.trailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class trailController implements trailService {

    private static final String ENDPOINT = "/trail";

    @GetMapping(ENDPOINT)
    public String hello() {
        return "hello";
    }

    @GetMapping(ENDPOINT + "/allocate")
    public Trail findTrailByLatitudeAndLongitude() {
        // this data is fixed for test for now
        return findNearestTrail(32, 163);
    }

    @Override
    public Trail findNearestTrail(double latitude, double longitude) {
        List<Location> trailLocations = new ArrayList<>();
        trailLocations.add(new Location(37.77630000000005, -122.41799999999995));
        trailLocations.add(new Location(37.79, -122.40));
        trailLocations.add(new Location(37.80, -122.3801));
        return new Trail("Hamilton Lake Trail (test)", trailLocations);
    }
}