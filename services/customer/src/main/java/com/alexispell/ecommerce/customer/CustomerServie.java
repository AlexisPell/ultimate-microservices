package com.alexispell.ecommerce.customer;

import com.alexispell.ecommerce.customer.dto.CustomerRequestDto;
import com.alexispell.ecommerce.customer.dto.CustomerResponseDto;
import com.alexispell.ecommerce.customer.dto.UpdateCustomerRequestDto;
import com.alexispell.ecommerce.exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServie {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequestDto dto) {
        var customer = repository.save(mapper.toCustomer(dto));
        return customer.getId().toString();
    }

    public void updateCustomer(@Valid UpdateCustomerRequestDto dto) {
        var customer = repository.findById(dto.id())
                .orElseThrow(() -> new CustomerNotFoundException(
            String.format("Customer with id '%s' not found", dto.id())
        ));
        mergeCustomer(customer, dto);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, UpdateCustomerRequestDto dto) {
        if (StringUtils.isNotBlank(dto.firstName())) {
            customer.setFirstName(dto.firstName());
        }
        if (StringUtils.isNotBlank(dto.lastName())) {
            customer.setLastName(dto.lastName());
        }
        if (StringUtils.isNotBlank(dto.email())) {
            customer.setEmail(dto.email());
        }
        if (dto.address() != null) {
            customer.setAddress(dto.address());
        }
    }

    public List<CustomerResponseDto> findAllCustomers(int page, int size) {
        return repository.findAll(PageRequest.of(page, size))
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return repository.existsById(customerId);
    }

    public CustomerResponseDto findById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id '%s' not found", customerId)));
    }

    public void deleteById(String customerId) {
        repository.deleteById(customerId);
    }
}
