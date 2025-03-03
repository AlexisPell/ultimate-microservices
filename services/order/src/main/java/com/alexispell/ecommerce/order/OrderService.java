package com.alexispell.ecommerce.order;

import com.alexispell.ecommerce.customer.CustomerClient;
import com.alexispell.ecommerce.customer.CustomerResponseDto;
import com.alexispell.ecommerce.exception.BusinessException;
import com.alexispell.ecommerce.kafka.OrderConfirmationDto;
import com.alexispell.ecommerce.kafka.OrderProducer;
import com.alexispell.ecommerce.orderLine.OrderLineRequestDto;
import com.alexispell.ecommerce.orderLine.OrderLineService;
import com.alexispell.ecommerce.payment.PaymentClient;
import com.alexispell.ecommerce.payment.PaymentRequestDto;
import com.alexispell.ecommerce.product.ProductClient;
import com.alexispell.ecommerce.product.PurchaseRequest;
import com.alexispell.ecommerce.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequestDto reqDto) {
        // check the customer existance -> customer-service (via OpenFeign)
        CustomerResponseDto customer = customerClient.findCustomerById(reqDto.customerId())
                .orElseThrow(() -> new BusinessException(String.format("Cannot create order. No customer exists with id %s", reqDto.customerId())));

        // purchase the product -> product-service (via RestTemplate)
        // TODO: This is not okay. May cause parallel calls and is not concurrent-safety.
        List<PurchaseResponse> products = productClient.purchaseProducts(reqDto.products());

        // persist order (without orderLines)
        var order = orderRepository.save(orderMapper.toOrder(reqDto));

        // persist orderLines for order
        for (PurchaseRequest purchaseRequest: reqDto.products()) {
            // TODO: bad practice - it takes exclusive pool connection to db for each save
            // Must be reworked to saveAll but let it be
            orderLineService.saveOrderLine(
                    new OrderLineRequestDto(
                            null, // orderLineId
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // start payment process
        var paymentRequest = new PaymentRequestDto(
                reqDto.amount(),
                reqDto.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        // send the order confirmation to notification service
        orderProducer.orderConfirmation(
                new OrderConfirmationDto(
                        reqDto.reference(),
                        reqDto.amount(),
                        reqDto.paymentMethod(),
                        customer,
                        products
                )
        );

        return order.getId();
    }

    public List<OrderResponseDto> findAll() {
        return orderRepository.findAll().stream().map(orderMapper::fromOrder).toList();
    }

    public OrderResponseDto findById(Integer orderId) {
        return orderRepository
                .findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %d not found", orderId)));
    }
}
