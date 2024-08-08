package com.adgomin.payment.service;

import com.adgomin.payment.mapper.PaymentMapper;
import org.springframework.stereotype.Service;

@Service(value = "com.adgomin.payment.service.PaymentService")
public class PaymentService {
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }
}
