package com.alexispell.ecommerce.customer;

import feign.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}"
)
public interface CustomerClient {
    @GetMapping("/{customerId}")
    Optional<CustomerResponseDto> findCustomerById(@PathVariable("customerId") String customerId);
}
