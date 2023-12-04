package com.cas735.finalproject.heartratemonitorsrv.business.entities;

import lombok.*;

import java.util.Objects;
import java.time.LocalDateTime;

@NoArgsConstructor @RequiredArgsConstructor
@Getter @Setter
@ToString
public class Heartrate {

    private @NonNull Long workoutId;
    private @NonNull LocalDateTime dateTime; // date and time of the heart rate record
    private @NonNull Integer heartrate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Heartrate record = (Heartrate) o;
        return Objects.equals(dateTime, record.dateTime) && heartrate.equals(record.heartrate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, heartrate);
    }

}
