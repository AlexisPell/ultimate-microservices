package com.alexispell.ecommerce.dto;

import com.alexispell.ecommerce.customer.Address;
import jakarta.validation.constraints.NotEmpty;

public record UpdateCustomerRequestDto (
    @NotEmpty(message = "Id is required for update")
    String id,
    String firstName,
    String lastName,
    String email,
    Address address
) {}
