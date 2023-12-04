package com.cas735.lab6.heartratemonitorsrv.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import javax.persistence.Column;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndWorkoutRequest {
    private Long id;
    private LocalDateTime endDateTime;
}
