package com.alexispell.ecommerce.product;

import com.alexispell.ecommerce.category.Category.Category;
import com.alexispell.ecommerce.product.dto.ProductPurchaseResponseDto;
import com.alexispell.ecommerce.product.dto.ProductRequestDto;
import com.alexispell.ecommerce.product.dto.ProductResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductMapper {
    public Product toProduct(@Valid ProductRequestDto dto) {
        return Product
                .builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .availableQuantity(dto.availableQuantity())
                .category(Category
                        .builder()
                        .id(dto.categoryId())
                        .build())
                .build();
    }

    public ProductResponseDto toProductResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponseDto toProductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
