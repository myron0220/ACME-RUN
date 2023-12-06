package com.cas735.finalproject.biometricsrv.adapters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cas735.finalproject.biometricsrv.RabbitConfiguration;
import com.cas735.finalproject.biometricsrv.business.HeartrateManager;
import com.cas735.finalproject.biometricsrv.business.entities.Heartrate;

@Component
@Slf4j
public class HeartrateListenController {

    @Autowired
    private HeartrateManager heartrateManager;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConfiguration.HEARTRATE_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(
                    value = RabbitConfiguration.HEARTRATE_EXCHANGE_NAME, ignoreDeclarationExceptions = "true"),
                    key = RabbitConfiguration.HEARTRATE_ROUTING_KEY))
    public void receiveHeartrateUpdate(Heartrate req) {
        log.info("Reading heartrate & position: '" + req + "'");
        heartrateManager.create(req);
    }

}