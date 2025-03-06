package com.alexispell.ecommerce.kafka;

import com.alexispell.ecommerce.email.EmailService;
import com.alexispell.ecommerce.kafka.order.OrderCreatedConfirmation;
import com.alexispell.ecommerce.kafka.payment.PaymentCompletedConfirmation;
import com.alexispell.ecommerce.notification.Notification;
import com.alexispell.ecommerce.notification.NotificationRepository;
import com.alexispell.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-completed")
    public void consumePaymentCompletedNotification(PaymentCompletedConfirmation paymentConfirmation) throws MessagingException {
        log.info(String.format("Received payment-completed topic:: %s", paymentConfirmation));
        notificationRepository.save(
                Notification
                        .builder()
                        .type(NotificationType.PAYMENT_COMPLETED)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
        emailService.sendPaymentCompletedEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-created")
    public void consumeOrderCreatedNotification(OrderCreatedConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Received order-created topic:: %s", orderConfirmation));
        notificationRepository.save(
                Notification
                        .builder()
                        .type(NotificationType.ORDER_CREATED)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        var customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}
