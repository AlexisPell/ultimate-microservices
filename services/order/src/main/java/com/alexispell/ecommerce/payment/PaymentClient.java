package com.alexispell.ecommerce.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentClient {
    private final WebClient webClient;

    public Mono<Integer> requestOrderPayment(PaymentRequestDto payment) {
        return webClient
                .post()
                .uri("")
                .bodyValue(payment)
                .retrieve()
                .bodyToMono(Integer.class);
        // on client side use requestOrderPayment().block() to block, or CompletableFuture for async
    }
}
