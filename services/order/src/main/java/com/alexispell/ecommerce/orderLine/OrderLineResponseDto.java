package com.alexispell.ecommerce.orderLine;

public record OrderLineResponseDto(
        Integer id,
        Integer productId,
        double quantity
) {}
