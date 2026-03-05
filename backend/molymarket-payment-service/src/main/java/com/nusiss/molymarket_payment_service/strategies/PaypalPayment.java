package com.nusiss.molymarket_payment_service.strategies;

import java.util.Map;

import com.nusiss.molymarket_payment_service.strategies.interfaces.PaymentStrategy;
import org.springframework.stereotype.Service;


@Service("paypal")
public class PaypalPayment implements PaymentStrategy {

    @Override
    public void processPayment(double amount, Map<String, String> details) {
        String paypalEmail = details.get("paypalEmail");
        if (paypalEmail == null) {
            throw new IllegalArgumentException("PayPal email is missing");
        }

        System.out.println("Processing PayPal payment of $" + amount +
                " with email " + paypalEmail);
    }
}