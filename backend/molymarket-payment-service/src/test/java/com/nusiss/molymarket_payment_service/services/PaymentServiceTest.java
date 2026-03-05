package com.nusiss.molymarket_payment_service.services;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nusiss.molymarket_payment_service.factories.PaymentStrategyFactory;
import com.nusiss.molymarket_payment_service.strategies.interfaces.PaymentStrategy;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentStrategyFactory strategyFactory;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void testProcessPayment() {
        PaymentStrategy mockStrategy = mock(PaymentStrategy.class);
        when(strategyFactory.getStrategy(anyString())).thenReturn(mockStrategy);
        
        paymentService.processPayment("cash", 100.0, new HashMap<>());
        
        verify(strategyFactory).getStrategy("cash");
        verify(mockStrategy).processPayment(anyDouble(), anyMap());
    }
}
