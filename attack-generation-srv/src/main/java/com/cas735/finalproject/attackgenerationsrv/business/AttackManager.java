package com.cas735.finalproject.attackgenerationsrv.business;

import com.cas735.finalproject.attackgenerationsrv.ports.AttackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AttackManager {
    AttackService attackService;

    public AttackManager(AttackService attackService) {
        this.attackService = attackService;
    }

    @Scheduled(fixedRate=100000)
    public void sendData() {
        log.info("Sending attack.");
        attackService.sendAttack();
    }
}
