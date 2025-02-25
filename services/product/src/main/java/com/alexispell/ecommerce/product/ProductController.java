package com.alexispell.ecommerce.product;

import com.alexispell.ecommerce.product.dto.ProductPurchaseRequestDto;
import com.alexispell.ecommerce.product.dto.ProductPurchaseResponseDto;
import com.alexispell.ecommerce.product.dto.ProductRequestDto;
import com.alexispell.ecommerce.product.dto.ProductResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequestDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponseDto>> purchaseProducts(
            @RequestBody @Valid List<ProductPurchaseRequestDto> dtos
    ) {
        return ResponseEntity.ok(productService.purchaseProducts(dtos));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> findById(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.findAll(page, size));
        //        PageRequest.of(page, size)
    }
}
