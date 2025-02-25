package com.alexispell.ecommerce.product;

import com.alexispell.ecommerce.exception.ProductPurchaseException;
import com.alexispell.ecommerce.product.dto.ProductPurchaseRequestDto;
import com.alexispell.ecommerce.product.dto.ProductPurchaseResponseDto;
import com.alexispell.ecommerce.product.dto.ProductRequestDto;
import com.alexispell.ecommerce.product.dto.ProductResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(@Valid ProductRequestDto productDto) {
        var product = productMapper.toProduct(productDto);

        return productRepository.save(product).getId();
    }

    @Transactional(rollbackOn = Exception.class)
    public List<ProductPurchaseResponseDto> purchaseProducts(List<ProductPurchaseRequestDto> requestDtos) {
        var productIds = requestDtos
                .stream()
                .map(ProductPurchaseRequestDto::productId)
                .toList();
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }

        var storedRequestDto = requestDtos
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequestDto::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponseDto>();

        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequestDto.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException(String.format("Insufficient quantity of product %s. Id: %s", product.getName(), product.getId()));
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(productMapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponseDto findById(Integer productId) {
        return productRepository
                .findById(productId)
                .map(productMapper::toProductResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s was not found", productId)));
    }

    public List<ProductResponseDto> findAll(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }
}
