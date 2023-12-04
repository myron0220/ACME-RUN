package com.cas735.finalproject.gpsmonitorsrv.business.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@NoArgsConstructor @RequiredArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Workout {

    private @Id Long id;
    private @NonNull String name;
    private @NonNull LocalDateTime startTime;
    private LocalDateTime endTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(id, workout.id) && name.equals(workout.name) && startTime.equals(workout.endTime) && endTime.equals(workout.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startTime, endTime);
    }

}
