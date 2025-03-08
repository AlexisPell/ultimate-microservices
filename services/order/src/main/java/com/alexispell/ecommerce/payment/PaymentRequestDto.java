package com.alexispell.ecommerce.payment;

import com.alexispell.ecommerce.customer.CustomerResponseDto;
import com.alexispell.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequestDto(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponseDto customer
) {}
