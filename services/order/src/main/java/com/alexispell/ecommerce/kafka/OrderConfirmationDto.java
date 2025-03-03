package com.alexispell.ecommerce.kafka;

import com.alexispell.ecommerce.customer.CustomerResponseDto;
import com.alexispell.ecommerce.order.PaymentMethod;
import com.alexispell.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmationDto(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponseDto customer,
        List<PurchaseResponse> products
) {}
