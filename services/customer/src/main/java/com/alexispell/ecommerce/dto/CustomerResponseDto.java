package com.alexispell.ecommerce.dto;


import com.alexispell.ecommerce.customer.Address;
import org.bson.types.ObjectId;

public record CustomerResponseDto(
        ObjectId id,
        String firstName,
        String lastName,
        String email,
        Address address
) {}
