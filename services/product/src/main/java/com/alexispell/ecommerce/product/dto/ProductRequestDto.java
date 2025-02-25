package com.alexispell.ecommerce.product.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDto(
        @NotEmpty(message = "Name is required for product")
        String name,
        @NotEmpty(message = "Description is required for product")
        String description,
        @Positive(message = "Quantity must be positive")
        double availableQuantity,
        @Positive(message = "Price must be positive")
        BigDecimal price,
        @NotEmpty(message = "Category is required for product")
        Integer categoryId
) {}
