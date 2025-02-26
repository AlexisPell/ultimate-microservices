package com.alexispell.ecommerce.order;

import com.alexispell.ecommerce.orderLine.OrderLine;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequestDto dto) {
        return Order
                .builder()
                .reference(dto.reference())
                .totalAmount(dto.amount())
                .paymentMethod(dto.paymentMethod())
                .customerId(dto.customerId())
                .build();
    }
}
