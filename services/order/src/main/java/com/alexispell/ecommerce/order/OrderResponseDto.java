package com.alexispell.ecommerce.order;

import java.math.BigDecimal;

public record OrderResponseDto(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
