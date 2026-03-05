package com.nusiss.molymarket_payment_service.factories;

import java.util.Map;

import com.nusiss.molymarket_payment_service.strategies.interfaces.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class PaymentStrategyFactory {

    private final Map<String, PaymentStrategy> strategies;

    public PaymentStrategy getStrategy(String strategyType){
        PaymentStrategy strategy = strategies.get(strategyType);
        if (strategy == null){
            throw new IllegalArgumentException("Invalid payment type: " + strategyType);
        }
        return strategy;
    }

}