package com.cas735.finalproject.heartratemonitorsrv.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserScoreRequest {
    private String username;
    LocalDateTime requestTime;
}
