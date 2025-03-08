package com.service.management.notification.consumer;

import com.service.management.notification.dto.JsonResponseDto;
import com.service.management.notification.dto.NotificationDto;
import com.service.management.notification.dto.NotificationRequestDto;
import com.service.management.notification.producer.RabbitMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@EnableRabbit
@Slf4j
@Service
public class RabbitMQJsonConsumer {

    private final RabbitMQProducer rabbitMQProducer;

    public RabbitMQJsonConsumer(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @RabbitListener(queues = "${rabbitmq.queue.json.name}")
    public void consumeJsonMessage(NotificationRequestDto notificationRequestDto) {
        log.info("RabbitMQ JSON Message for : {}", notificationRequestDto.recipient());
        JsonResponseDto jsonResponseDto = new JsonResponseDto("SUCCESSSSS.......");
        rabbitMQProducer.sendResponseMessage(jsonResponseDto);
    }
}
