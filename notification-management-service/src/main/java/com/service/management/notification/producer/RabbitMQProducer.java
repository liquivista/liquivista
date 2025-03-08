package com.service.management.notification.producer;

import com.service.management.notification.dto.JsonResponseDto;
import com.service.management.notification.dto.NotificationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.response.exchange.name}")
    private String responseExchange;

    @Value("${rabbitmq.response.routing.key}")
    private String responseRoutingKey;

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendResponseMessage(JsonResponseDto response){
        log.info("RabbitMQ Response Message : {}", response);
        rabbitTemplate.convertAndSend(responseExchange,responseRoutingKey,response);
    }
}
