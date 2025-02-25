package com.alexispell.ecommerce.customer.dto;

import com.alexispell.ecommerce.customer.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CustomerRequestDto(
        @NotEmpty(message = "firstName is required")
        String firstName,
        @NotEmpty(message = "lastName is required")
        String lastName,
        @NotEmpty(message = "email is required")
        @Email(message = "Email is not valid")
        String email,
        Address address
) {}