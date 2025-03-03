package com.alexispell.ecommerce.kafka;

import com.alexispell.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequestDto(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {}
