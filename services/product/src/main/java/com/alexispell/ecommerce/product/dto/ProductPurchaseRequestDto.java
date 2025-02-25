package com.alexispell.ecommerce.product.dto;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequestDto(
        @NotNull(message = "Product is mandatory")
        Integer productId,
        @NotNull(message = "Quantity is mandatory")
        Double quantity
) {}
