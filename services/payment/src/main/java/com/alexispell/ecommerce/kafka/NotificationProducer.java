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
public class NotificationProducer {
    private final KafkaTemplate<String, PaymentNotificationRequestDto> paymentNotificationKafkaTemplate;

    public void sendPaymentCompletedNotification(PaymentNotificationRequestDto request) {
        log.info("Sending payment completed notification with body: <{}>", request);
        Message<PaymentNotificationRequestDto> msg = MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.TOPIC, "payment-completed")
                .build();

        paymentNotificationKafkaTemplate.send(msg);
    }
}
