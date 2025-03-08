package com.alexispell.ecommerce.order;

import com.alexispell.ecommerce.customer.CustomerClient;
import com.alexispell.ecommerce.customer.CustomerResponseDto;
import com.alexispell.ecommerce.exception.BusinessException;
import com.alexispell.ecommerce.kafka.OrderConfirmationDto;
import com.alexispell.ecommerce.kafka.OrderProducer;
import com.alexispell.ecommerce.orderLine.OrderLineRequestDto;
import com.alexispell.ecommerce.orderLine.OrderLineService;
import com.alexispell.ecommerce.orderLine.SaveOrderLineDto;
import com.alexispell.ecommerce.payment.PaymentClient;
import com.alexispell.ecommerce.payment.PaymentRequestDto;
import com.alexispell.ecommerce.product.ProductClient;
import com.alexispell.ecommerce.product.PurchaseRequest;
import com.alexispell.ecommerce.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Transactional
    public Integer createOrder(OrderRequestDto reqDto) {
        // check the customer existance -> customer-service (via OpenFeign)
        System.out.println("Create Order payload:: " + reqDto.toString());
        CustomerResponseDto customer = customerClient.findCustomerById(reqDto.customerId())
                .orElseThrow(() -> new BusinessException(String.format("Cannot create order. No customer exists with id %s", reqDto.customerId())));

        // purchase the product -> product-service (via RestTemplate)
        // TODO: This is not okay. May corrupt data on errors w/o transactions but idk in pet project
        List<PurchaseResponse> products = productClient.purchaseProducts(reqDto.products());

        // persist order (without orderLines)
        var order = orderRepository.save(orderMapper.toOrder(reqDto));


        // persist orderLines for order
        var orderLinesDtos = new ArrayList<SaveOrderLineDto>();
        for (PurchaseRequest purchaseRequest: reqDto.products()) {
            orderLinesDtos.add(new SaveOrderLineDto(
                    order.getId(),
                    purchaseRequest.productId(),
                    purchaseRequest.quantity()
            ));
        }
//        orderLineService.saveAllOrderLines(orderLinesDtos);


        // start payment process
        var paymentRequest = new PaymentRequestDto(
                reqDto.amount(),
                reqDto.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        var paymentId = paymentClient.requestOrderPayment(paymentRequest);

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
