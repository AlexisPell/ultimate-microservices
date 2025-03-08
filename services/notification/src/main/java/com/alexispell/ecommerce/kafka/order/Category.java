package com.alexispell.ecommerce.kafka.order;

public record Category(
        Integer categoryId,
        String name,
        String description
) {
}
