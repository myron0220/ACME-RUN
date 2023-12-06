package com.cas735.finalproject.attackgenerationsrv.adapters;

import com.cas735.finalproject.attackgenerationsrv.RabbitConfiguration;
import com.cas735.finalproject.attackgenerationsrv.business.entities.Attack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cas735.finalproject.attackgenerationsrv.ports.AttackService;


@Slf4j
@Service
public class AttackClient implements AttackService {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public AttackClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendAttack() {
        Attack newAttack = new Attack("Zombie", 10);
        rabbitTemplate.convertAndSend(RabbitConfiguration.ATTACK_EXCHANGE_NAME,
                RabbitConfiguration.ATTACK_ROUTING_KEY, newAttack);
    }
}


