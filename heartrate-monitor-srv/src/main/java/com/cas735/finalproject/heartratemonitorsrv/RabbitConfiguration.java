package com.cas735.finalproject.heartratemonitorsrv;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;




@Configuration
public class RabbitConfiguration {

    public static final String HEARTRATE_EXCHANGE_NAME = "heartrate-exchange";

    public static final String HEARTRATE_ROUTING_KEY = "heartrate-routing";

    public static final String ATTACK_EXCHANGE_NAME = "attack-exchange";

    public static final String ATTACK_QUEUE_NAME = "attack-queue";

    public static final String ATTACK_ROUTING_KEY = "attack-routing";

    // https://stackoverflow.com/a/59970323
    @Bean
    public Jackson2JsonMessageConverter converter(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
