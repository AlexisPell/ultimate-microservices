package com.alexispell.ecommerce.kafka.order;

import com.alexispell.ecommerce.common.dto.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderCreatedConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<Product> products
) {}
