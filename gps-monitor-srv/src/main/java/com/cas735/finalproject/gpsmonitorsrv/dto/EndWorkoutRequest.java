package com.cas735.finalproject.gpsmonitorsrv.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndWorkoutRequest {
    private Long id;
    LocalDateTime endDateTime;
}
