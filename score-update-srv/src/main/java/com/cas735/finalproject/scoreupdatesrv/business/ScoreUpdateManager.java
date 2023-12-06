package com.cas735.finalproject.scoreupdatesrv.business;

import com.cas735.finalproject.scoreupdatesrv.business.entities.Score;
import com.cas735.finalproject.scoreupdatesrv.ports.ScoreupdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Scanner;

@Service
@Slf4j
public class ScoreUpdateManager {
    ScoreupdateService scoreupdateService;
    Score score = null;

    // this module simulates a score updater by registering a new score record and then sending updates, until it's turned off
    public ScoreUpdateManager(ScoreupdateService scoreupdateService) {
        this.scoreupdateService = scoreupdateService;
        Scanner scanner = new Scanner(System.in);
        log.info("------------------- Score update interface -----------------");
        log.info("Please enter your username: ");
        String userName = scanner.next();

        // register a new score
        while (this.score == null) {
            this.score = scoreupdateService.createScore(userName);
            // sleep for 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Integer i = 0; i < 5; i++)
        {
            log.info("------------------- Try update the score record -----------------");
            log.info("Iteration time 1" + Integer.toString(i));
            scoreupdateService.updateScore(this.score.getPersonId());
            log.info("Score record get updated");
        }

        log.info("------------------- Delete score record -----------------");

        scoreupdateService.deleteScore(this.score.getPersonId());

        log.info("Score record deleted");

    }
}

