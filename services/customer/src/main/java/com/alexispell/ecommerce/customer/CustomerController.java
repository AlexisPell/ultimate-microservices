package com.alexispell.ecommerce.customer;

import com.alexispell.ecommerce.customer.dto.CustomerRequestDto;
import com.alexispell.ecommerce.customer.dto.CustomerResponseDto;
import com.alexispell.ecommerce.customer.dto.UpdateCustomerRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServie service;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequestDto dto) {
        return ResponseEntity.ok(service.createCustomer(dto));
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody @Valid UpdateCustomerRequestDto dto) {
        service.updateCustomer(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.findAllCustomers(page, size));
    }

    @GetMapping("/exists/{customerId}")
    public ResponseEntity<Boolean> existsById(@PathVariable String customerId) {
        return ResponseEntity.ok(service.existsById(customerId));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> findById(@PathVariable String customerId) {
        return ResponseEntity.ok(service.findById(customerId));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteById(@PathVariable String customerId) {
        service.deleteById(customerId);
        return ResponseEntity.ok().build();
    }

}
