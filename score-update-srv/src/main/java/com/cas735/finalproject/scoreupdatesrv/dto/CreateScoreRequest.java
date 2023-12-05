package com.cas735.finalproject.scoreupdatesrv.dto;

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
public class CreateScoreRequest {
    private Long id;
    private String username;
    private Integer score;
}
