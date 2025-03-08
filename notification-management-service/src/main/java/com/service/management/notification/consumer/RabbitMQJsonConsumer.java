package com.service.management.notification.consumer;

import com.service.management.notification.dto.JsonResponseDto;
import com.service.management.notification.dto.NotificationRequestDto;
import com.service.management.notification.producer.RabbitMQProducer;
import com.service.management.notification.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@EnableRabbit
@Slf4j
@Service
public class RabbitMQJsonConsumer {

    private final RabbitMQProducer rabbitMQProducer;

    private final EmailService emailService;

    public RabbitMQJsonConsumer(RabbitMQProducer rabbitMQProducer, EmailService emailService) {
        this.rabbitMQProducer = rabbitMQProducer;
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.json.name}")
    public void consumeJsonMessage(NotificationRequestDto notificationRequestDto) {
        log.info("RabbitMQ JSON Message for : {}", notificationRequestDto.recipient());
        String response = emailService.sendEmail(notificationRequestDto);
        JsonResponseDto jsonResponseDto = new JsonResponseDto(response);
        rabbitMQProducer.sendResponseMessage(jsonResponseDto);
    }
}
