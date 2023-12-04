package com.cas735.finalproject.biometricsrv.business.entities;

import lombok.*;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @RequiredArgsConstructor
@Getter @Setter
@ToString
public class Heartrate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "heartrate_generator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "workout_id", insertable = false, updatable = false)
    private @NonNull Workout workout;

    @Column(name = "workout_id")
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
