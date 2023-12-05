package com.cas735.finalproject.trailprovidersrv.ports;

import com.cas735.finalproject.trailprovidersrv.business.entities.Trail;

public interface trailService {
    public Trail findNearestTrail(double latitude, double longitude);
}
