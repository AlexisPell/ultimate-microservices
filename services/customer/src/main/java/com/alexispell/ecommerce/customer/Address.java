package com.alexispell.ecommerce.customer;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Validated
public class Address {
    private String city;
    private String street;
    private String houseNumber;
    private String zipCode;
}
