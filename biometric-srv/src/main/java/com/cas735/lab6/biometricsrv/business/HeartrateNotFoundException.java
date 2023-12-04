package com.cas735.lab6.biometricsrv.business;

public class HeartrateNotFoundException extends RuntimeException {

    public HeartrateNotFoundException(Long id) {
        super("Could not find heartrate #" + id);
    }

}
