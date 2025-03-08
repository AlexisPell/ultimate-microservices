package com.alexispell.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequestDto orderReqDto) {
        return ResponseEntity.ok(orderService.createOrder(orderReqDto));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> findById(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.findById(orderId));
    }
}
