package com.alexispell.ecommerce.orderLine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequestDto orderLineRequestDto) {
        var orderLine = orderLineMapper.toOrderLine(orderLineRequestDto);
        return orderLineRepository.save(orderLine).getId();
    }

    public List<OrderLineResponseDto> findByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper::toOrderLineResponseDto)
                .toList();
    }
}
