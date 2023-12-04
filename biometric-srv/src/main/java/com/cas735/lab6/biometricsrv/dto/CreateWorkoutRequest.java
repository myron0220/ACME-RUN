package com.cas735.lab6.biometricsrv.dto;

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
public class CreateWorkoutRequest {
    private String username;
    private LocalDateTime startDateTime;
}
