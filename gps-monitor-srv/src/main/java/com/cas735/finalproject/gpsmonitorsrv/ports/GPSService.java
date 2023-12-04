package com.cas735.finalproject.gpsmonitorsrv.ports;

import com.cas735.finalproject.gpsmonitorsrv.business.entities.Workout;

import java.time.LocalDateTime;

public interface GPSService {

    void sendGPS(Double latitude, Double longitude);

}