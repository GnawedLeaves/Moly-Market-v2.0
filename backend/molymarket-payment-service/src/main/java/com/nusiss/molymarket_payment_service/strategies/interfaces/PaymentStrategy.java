package com.nusiss.molymarket_payment_service.strategies.interfaces;

import java.util.Map;

public interface PaymentStrategy {
    void processPayment(double amount, Map<String, String> details);
}
