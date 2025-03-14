package com.alexispell.ecommerce.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAll(Pageable pageable);

    List<Product> findAllByIdInOrderById(List<Integer> productIds);
}
