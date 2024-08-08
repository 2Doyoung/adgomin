package com.adgomin.payment.controller;

import com.adgomin.payment.service.PaymentService;
import org.springframework.stereotype.Controller;

@Controller(value = "com.adgomin.payment.controller.PaymentController")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
