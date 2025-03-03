package com.alexispell.ecommerce.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, OrderConfirmationDto> orderTopicKafkaTemplate;

    public void orderConfirmation(OrderConfirmationDto orderConfirmationDto) {
        log.info("Sending order confirmation: {}", orderConfirmationDto);
        Message<OrderConfirmationDto> msg = MessageBuilder
                .withPayload(orderConfirmationDto)
                .setHeader(KafkaHeaders.TOPIC, "order-created")
                .build();

        orderTopicKafkaTemplate.send(msg);
    }
}
