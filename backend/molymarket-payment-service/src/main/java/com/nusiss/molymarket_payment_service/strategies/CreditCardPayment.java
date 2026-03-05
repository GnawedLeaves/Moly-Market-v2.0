package com.nusiss.molymarket_payment_service.strategies;

import java.util.Map;

import com.nusiss.molymarket_payment_service.strategies.interfaces.PaymentStrategy;
import org.springframework.stereotype.Service;


@Service("creditCard")
public class CreditCardPayment implements PaymentStrategy {

    @Override
    public void processPayment(double amount, Map<String, String> details) {
        String cardNumber = details.get("cardNumber");
        String cardHolder = details.get("cardHolder");

        if (cardNumber == null || cardHolder == null) {
            throw new IllegalArgumentException("Card details are missing");
        }

        System.out.println("Processing credit card payment of $" + amount +
                " with card " + cardNumber + " belonging to " + cardHolder);
    }
}