package com.nusiss.molymarket_payment_service.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nusiss.molymarket_payment_service.strategies.interfaces.PaymentStrategy;

public class PaymentStrategyFactoryTest {

    private PaymentStrategyFactory factory;
    private Map<String, PaymentStrategy> strategies;
    private PaymentStrategy mockStrategy;

    @BeforeEach
    void setUp() {
        strategies = new HashMap<>();
        mockStrategy = mock(PaymentStrategy.class);
        strategies.put("cash", mockStrategy);
        factory = new PaymentStrategyFactory(strategies);
    }

    @Test
    void testGetStrategy() {
        PaymentStrategy result = factory.getStrategy("cash");
        assertEquals(mockStrategy, result);
    }

    @Test
    void testGetStrategyNotFound() {
        assertThrows(IllegalArgumentException.class, () -> factory.getStrategy("invalid"));
    }
}
