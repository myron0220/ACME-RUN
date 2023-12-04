package com.cas735.finalproject.gpsmonitorsrv.business.entities;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class GPS {

    private @NonNull Long workoutId;
    private @NonNull LocalDateTime dateTime; // date and time of the heart rate record

    private @NonNull Double latitude;

    private @NonNull Double longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GPS gps = (GPS) o;
        return Objects.equals(workoutId, gps.workoutId)
                && Objects.equals(dateTime, gps.dateTime)
                && Objects.equals(latitude, gps.latitude)
                && Objects.equals(longitude, gps.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workoutId, dateTime, latitude, longitude);
    }

}
