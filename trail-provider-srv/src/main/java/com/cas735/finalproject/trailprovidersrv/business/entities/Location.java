package com.cas735.finalproject.trailprovidersrv.business.entities;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Location {

    private @NonNull double latitude;
    private @NonNull double longitude;

}
