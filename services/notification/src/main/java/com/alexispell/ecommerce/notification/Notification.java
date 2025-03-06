package com.alexispell.ecommerce.notification;

import com.alexispell.ecommerce.kafka.order.OrderCreatedConfirmation;
import com.alexispell.ecommerce.kafka.payment.PaymentCompletedConfirmation;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Document
public class Notification {
    @Id
    private String id;

    private NotificationType type;
    private LocalDateTime notificationDate;
    private OrderCreatedConfirmation orderConfirmation;
    private PaymentCompletedConfirmation paymentConfirmation;
}
