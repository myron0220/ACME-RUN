package com.cas735.finalproject.scoreupdatesrv.business.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;
import java.time.LocalDateTime;

@NoArgsConstructor @RequiredArgsConstructor
@Getter @Setter
@ToString
public class Score {
    private @NonNull Long personId;
    private @NonNull String username;
    private @NonNull Integer score;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return Objects.equals(personId, score1.personId) && score.equals(score1.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, score);
    }
}
