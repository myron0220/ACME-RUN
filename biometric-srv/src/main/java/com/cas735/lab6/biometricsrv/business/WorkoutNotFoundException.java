package com.cas735.lab6.biometricsrv.business;

public class WorkoutNotFoundException extends RuntimeException {

    public WorkoutNotFoundException(Long id) {
        super("Could not find workout #" + id);
    }

}
