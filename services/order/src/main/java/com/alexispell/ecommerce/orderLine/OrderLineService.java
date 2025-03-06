package com.alexispell.ecommerce.orderLine;

import com.alexispell.ecommerce.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

//    public Integer saveOrderLine(OrderLineRequestDto orderLineRequestDto) {
//        var orderLine = orderLineMapper.toOrderLine(orderLineRequestDto);
//        return orderLineRepository.save(orderLine).getId();
//    }

    public List<Integer> saveAllOrderLines(List<SaveOrderLineDto> orderLineRequestDtos) {
        try {
            var orderLines = new ArrayList<OrderLine>();
            for (SaveOrderLineDto orderLineRequestDto: orderLineRequestDtos) {
                orderLines.add(orderLineMapper.toOrderLine(orderLineRequestDto));
            }
            return orderLineRepository
                    .saveAll(orderLines)
                    .stream()
                    .map(OrderLine::getId)
                    .collect(Collectors.toList());

        } catch (ObjectOptimisticLockingFailureException e) {
            log.info("ERROR INFO::: " + e.toString());
            throw new BusinessException("Optimistic locking failure: Order line was modified concurrently::" + e.getMessage());
        } catch (DataAccessException e) {
            throw new BusinessException("Database error occurred while saving order lines:: " + e.getMessage());
        } catch (Exception e) {
            throw new BusinessException("Unexpected error while saving order lines:: " + e.getMessage());
        }
    }

    public List<OrderLineResponseDto> findByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper::toOrderLineResponseDto)
                .toList();
    }
}
