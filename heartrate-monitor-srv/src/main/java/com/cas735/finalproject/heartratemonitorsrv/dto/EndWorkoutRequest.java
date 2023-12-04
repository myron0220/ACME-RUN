package com.cas735.finalproject.heartratemonitorsrv.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndWorkoutRequest {
    private Long id;
    private LocalDateTime endDateTime;
}
