package com.alexispell.ecommerce.customer.dto;


import com.alexispell.ecommerce.customer.Address;
import org.bson.types.ObjectId;

public record CustomerResponseDto(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {}
