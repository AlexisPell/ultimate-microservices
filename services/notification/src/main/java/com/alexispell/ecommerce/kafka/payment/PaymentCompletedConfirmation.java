package com.alexispell.ecommerce.kafka.payment;

import com.alexispell.ecommerce.common.dto.PaymentMethod;

import java.math.BigDecimal;

public record PaymentCompletedConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {}
