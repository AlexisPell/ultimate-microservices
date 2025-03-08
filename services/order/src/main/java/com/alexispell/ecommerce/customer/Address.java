package com.alexispell.ecommerce.customer;

public record Address(
        String city,
        String street,
        String houseNumber,
        String zipCode
) {}
