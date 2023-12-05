package com.cas735.finalproject.heartratemonitorsrv.business.entities;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

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

