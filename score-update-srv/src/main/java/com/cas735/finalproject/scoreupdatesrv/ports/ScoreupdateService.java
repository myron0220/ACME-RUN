package com.cas735.finalproject.scoreupdatesrv.ports;

import com.cas735.finalproject.scoreupdatesrv.business.entities.Score;

import java.time.LocalDateTime;

public interface ScoreupdateService {

    void deleteScore(Long personId);
    Score createScore(String username);
    void updateScore(Long personId);

}
