package com.alexispell.ecommerce.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
    String id,
    @NotEmpty(message = "firstName is requiresd")
    String firstName,
    @NotEmpty(message = "lastName is requiresd")
    String lastName,
    @NotEmpty(message = "email is requiresd")
    @Email(message = "Email is not valid")
    String email
) {
}
