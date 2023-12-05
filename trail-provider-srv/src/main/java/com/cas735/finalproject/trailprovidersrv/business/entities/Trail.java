package com.cas735.finalproject.trailprovidersrv.business.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;

@NoArgsConstructor @RequiredArgsConstructor
@Getter @Setter
@ToString
public class Trail {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private @NonNull String trailName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trail_id")
    private @NonNull List<Location> locations;

//    public Trail(String s, List<Location> trailLocations) {
//        trailName = s;
//        trailLocations = trailLocations;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trail trail = (Trail) o;
        return Objects.equals(trailName, trail.trailName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trailName);
    }

}

