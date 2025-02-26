package com.alexispell.ecommerce.orderLine;

import com.alexispell.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {

    public OrderLine toOrderLine(OrderLineRequest res) {
        return OrderLine
                .builder()
                .id(res.orderId())
                .quantity(res.quantity())
                .order(Order
                        .builder()
                        .id(res.orderId())
                        .build())
                .productId(res.productId())
                .build();
    }
}
