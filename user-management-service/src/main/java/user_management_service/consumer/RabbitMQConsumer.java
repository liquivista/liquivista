package user_management_service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import user_management_service.dto.JsonResponseDto;

@EnableRabbit
@Slf4j
@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "${rabbitmq.response.queue.name}")
    public void consumeMessage(JsonResponseDto response) {
        log.info("RabbitMQ Response Message : {}", response);
    }
}
