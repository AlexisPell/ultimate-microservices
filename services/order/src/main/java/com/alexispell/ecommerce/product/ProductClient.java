package com.alexispell.ecommerce.product;

import com.alexispell.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

// OBSOLETE APPROACH
@Service
@RequiredArgsConstructor
public class ProductClient {
    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<List<PurchaseRequest>> request = new HttpEntity<>(requestBody, headers);
        var responseType = new ParameterizedTypeReference<List<PurchaseResponse>>() {};

        var purchaseProductUrl = productUrl + "/purchase";

        ResponseEntity<List<PurchaseResponse>> responseEntity =
                restTemplate
                        .exchange(purchaseProductUrl, HttpMethod.POST, request, responseType);

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occured while processing the product purchase");
        }
        return responseEntity.getBody();
    }
}
