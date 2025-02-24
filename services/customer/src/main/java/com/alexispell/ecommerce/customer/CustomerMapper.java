package com.alexispell.ecommerce.customer;

import com.alexispell.ecommerce.dto.CustomerRequestDto;
import com.alexispell.ecommerce.dto.CustomerResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(CustomerRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return Customer
                .builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .address(dto.address())
                .build();
    }

    public CustomerResponseDto fromCustomer(Customer customer) {
        if (customer == null) {return null;}

        return new CustomerResponseDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
