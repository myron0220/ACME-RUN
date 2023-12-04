package com.cas735.lab6.biometricsrv.adapters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cas735.lab6.biometricsrv.RabbitConfiguration;
import com.cas735.lab6.biometricsrv.business.HeartrateManager;
import com.cas735.lab6.biometricsrv.business.WorkoutManager;
import com.cas735.lab6.biometricsrv.business.entities.Heartrate;
import com.cas735.lab6.biometricsrv.business.entities.Workout;

@Component
@Slf4j
public class BiometricListenController {

    @Autowired
    private HeartrateManager heartrateManager;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConfiguration.QUEUE_NAME, durable = "true"),
            exchange = @Exchange(
                    value = RabbitConfiguration.EXCHANGE_NAME, ignoreDeclarationExceptions = "true"),
                    key = RabbitConfiguration.ROUTING_KEY))
    public void receiveHeartrateUpdate(Heartrate req) {
        log.info("Reading message: '" + req + "'");
        heartrateManager.create(req);
    }

}