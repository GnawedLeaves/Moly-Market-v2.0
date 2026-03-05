package com.nusiss.molymarket_payment_service.services;

import java.util.Map;

import com.nusiss.molymarket_payment_service.factories.PaymentStrategyFactory;
import com.nusiss.molymarket_payment_service.strategies.interfaces.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentStrategyFactory strategyFactory;
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);


    public void processPayment(String paymentType, double amount, Map<String, String> details){
        PaymentStrategy paymentStrategy = strategyFactory.getStrategy(paymentType);

        logger.info("PROCESSING PAYMENT of type {} for amount {}", paymentType, amount);
        paymentStrategy.processPayment(amount, details);

        logger.info("PAYMENT PROCESSED SUCCESSFULLY");
    }
}
