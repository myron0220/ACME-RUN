package com.cas735.lab6.biometricsrv.ports;

import java.time.LocalDateTime;

public interface HeartrateUpdate {

    boolean updateHeartrate(Long workoutId, LocalDateTime dateTime, int heartrate);

}
