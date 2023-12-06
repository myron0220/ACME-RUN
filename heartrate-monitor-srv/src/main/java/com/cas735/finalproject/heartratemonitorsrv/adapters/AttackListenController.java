package com.cas735.finalproject.heartratemonitorsrv.adapters;

import com.cas735.finalproject.heartratemonitorsrv.RabbitConfiguration;
import com.cas735.finalproject.heartratemonitorsrv.business.AttackState;
import com.cas735.finalproject.heartratemonitorsrv.business.entities.Attack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Slf4j
public class AttackListenController {
    private Scanner scanner = new Scanner(System.in);
    @Autowired
    AttackState attackState;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConfiguration.ATTACK_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(
                    value = RabbitConfiguration.ATTACK_EXCHANGE_NAME, ignoreDeclarationExceptions = "true"),
                    key = RabbitConfiguration.ATTACK_ROUTING_KEY))
    public void receiveHeartrateUpdate(Attack attack) {

        this.attackState.setAttacked();
        log.info("------------------------------------------------------");
        log.info("You are being attacked '" + attack.toString() + "'");
        log.info("Please enter 1 - 3 to response to attack.");
        log.info("------------------------------------------------------");

        int option = scanner.nextInt();
        log.info("OK. You choose " + option + ". ");
        log.info("------------------------------------------------------");
        this.attackState.setNotAttacked();
    }

}