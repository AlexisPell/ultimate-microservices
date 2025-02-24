package com.alexispell.ecommerce.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CustomerRepository extends MongoRepository<Customer, String> {
    Page<Customer> findAll(Pageable pageable);
}
