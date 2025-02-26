package com.alexispell.ecommerce.orderLine;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Integer id,
        @NotNull(message = "orderId is mandatory")
        Integer orderId,
        @NotNull(message = "productId is mandatory")
        Integer productId,
        @NotNull(message = "quantity is mandatory")
        @Positive(message = "quantity is positive")
        double quantity
) {
}
