package user_management_service.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user_management_service.dto.NotificationDto;

@Service
@Slf4j
public class RabbitMQJsonProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingJsonKey;

    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(NotificationDto notificationDto){
        log.info("RabbitMQ JSON Message : {}", notificationDto.recipient());
        rabbitTemplate.convertAndSend(exchange,routingJsonKey,notificationDto);
    }
}
