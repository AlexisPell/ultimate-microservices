package com.alexispell.ecommerce.order;

import com.alexispell.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDto(
//        Integer id,
        String reference,
        @Positive(message = "Order should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method should exist")
        PaymentMethod paymentMethod,
        @NotEmpty(message = "CustomerId should exist")
        String customerId,
        @NotEmpty(message = "Should purchase at least one product")
        List<PurchaseRequest> products
) {
}
