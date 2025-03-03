package com.alexispell.ecommerce.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequestDto dto) {
        return Payment
                .builder()
                .amount(dto.amount())
                .paymentMethod(dto.paymentMethod())
                .orderId(dto.orderId())
                .build();
    }
}
