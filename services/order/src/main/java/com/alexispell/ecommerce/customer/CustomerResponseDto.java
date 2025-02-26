package com.alexispell.ecommerce.customer;

public record CustomerResponseDto(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
