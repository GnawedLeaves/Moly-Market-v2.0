package com.nusiss.molymarket_payment_service.strategies;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class CashPaymentTest {

    @Test
    void testProcessPayment() {
        CashPayment cashPayment = new CashPayment();
        cashPayment.processPayment(100.0, new HashMap<>());
        // Simple verification that it doesn't throw exception
    }
}
