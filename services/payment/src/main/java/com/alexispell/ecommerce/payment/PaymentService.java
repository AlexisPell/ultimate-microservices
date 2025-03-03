package com.alexispell.ecommerce.payment;

import com.alexispell.ecommerce.kafka.NotificationProducer;
import com.alexispell.ecommerce.kafka.PaymentNotificationRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequestDto paymentRequestDto) {
        // create payment
        var payment = paymentRepository.save(paymentMapper.toPayment(paymentRequestDto));

        // send notification "payment completed"
        notificationProducer.sendPaymentCompletedNotification(
                new PaymentNotificationRequestDto(
                        paymentRequestDto.orderReference(),
                        paymentRequestDto.amount(),
                        paymentRequestDto.paymentMethod(),
                        paymentRequestDto.customer().firstName(),
                        paymentRequestDto.customer().lastName(),
                        paymentRequestDto.customer().email()
                )
        );

        return payment.getId();
    }
}
