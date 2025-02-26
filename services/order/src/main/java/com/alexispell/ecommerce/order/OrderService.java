package com.alexispell.ecommerce.order;

import com.alexispell.ecommerce.customer.CustomerClient;
import com.alexispell.ecommerce.exception.BusinessException;
import com.alexispell.ecommerce.orderLine.OrderLineRequest;
import com.alexispell.ecommerce.orderLine.OrderLineService;
import com.alexispell.ecommerce.product.ProductClient;
import com.alexispell.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(OrderRequestDto reqDto) {
        // check the customer existance -> customer-service (via OpenFeign)
        var customer = customerClient.findCustomerById(reqDto.customerId())
                .orElseThrow(() -> new BusinessException(String.format("Cannot create order. No customer exists with id %s", reqDto.customerId())));

        // purchase the product -> product-service (via RestTemplate)
        // TODO: This is not okay. May cause parallel calls and is not concurrent-safety.
        productClient.purchaseProducts(reqDto.products());

        // persist order (without orderLines)
        var order = orderRepository.save(orderMapper.toOrder(reqDto));

        // persist orderLines for order
        for (PurchaseRequest purchaseRequest: reqDto.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null, // orderLineId
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // start payment process

        // send the order confirmation to notification service


        return null;
    }
}
