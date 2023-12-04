package com.cas735.finalproject.biometricsrv.business.entities;

import lombok.*;

import javax.persistence.*;

import java.util.Objects;
import java.time.LocalDateTime;

@Entity
@Table(name = "workouts")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout_generator")
    private Long id;

    private @NonNull String username;
    private @NonNull LocalDateTime startTime;
    private LocalDateTime endTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(id, workout.id) && username.equals(workout.username) && startTime.equals(workout.endTime) && endTime.equals(workout.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, startTime, endTime);
    }

}
