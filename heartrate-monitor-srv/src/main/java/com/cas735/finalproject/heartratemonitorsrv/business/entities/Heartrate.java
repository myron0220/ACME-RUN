package com.cas735.finalproject.heartratemonitorsrv.business.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;
import java.time.LocalDateTime;

@NoArgsConstructor @RequiredArgsConstructor
@Getter @Setter
@ToString
public class Heartrate {
    private @NonNull Long workoutId;
    private @NonNull LocalDateTime dateTime; // date and time of the heart rate record
    private @NonNull Integer heartrate;

    private @NonNull Double latitude;

    private @NonNull Double longitude;

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
