package com.nusiss.molymarket_payment_service.strategies;

import java.util.Map;

import com.nusiss.molymarket_payment_service.strategies.interfaces.PaymentStrategy;
import org.springframework.stereotype.Service;


@Service("cash")
public class CashPayment implements PaymentStrategy {

    @Override
    public void processPayment(double amount, Map<String, String> details) {
        System.out.println("Processing cash payment of $" + amount);
    }

}