package com.nusiss.molymarket_payment_service.controllers;

import com.nusiss.molymarket_payment_service.dtos.RequestPaymentDto;
import com.nusiss.molymarket_payment_service.services.PaymentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping
    public ResponseEntity<String> makePayment(@RequestBody RequestPaymentDto requestBody) {
        String paymentType = requestBody.getPaymentType();
        Double amount = requestBody.getAmount();
        Map<String, String> details = requestBody.getDetails();

        logger.info("PAYMENT CONTROLLER: {} {} {}", paymentType, amount, details);
        paymentService.processPayment(paymentType, amount, details);
        return ResponseEntity.ok("Payment successful using " + paymentType);
    }

}