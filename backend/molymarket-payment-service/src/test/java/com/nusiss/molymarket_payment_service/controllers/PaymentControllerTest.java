package com.nusiss.molymarket_payment_service.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nusiss.molymarket_payment_service.dtos.RequestPaymentDto;
import com.nusiss.molymarket_payment_service.services.PaymentService;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private RequestPaymentDto requestPaymentDto;

    @BeforeEach
    void setUp() {
        requestPaymentDto = new RequestPaymentDto();
        requestPaymentDto.setPaymentType("cash");
        requestPaymentDto.setAmount(100.0);
        requestPaymentDto.setDetails(new HashMap<>());
    }

    @Test
    void testMakePayment() {
        doNothing().when(paymentService).processPayment(anyString(), anyDouble(), anyMap());
        
        ResponseEntity<String> response = paymentController.makePayment(requestPaymentDto);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment successful using cash", response.getBody());
        verify(paymentService).processPayment("cash", 100.0, requestPaymentDto.getDetails());
    }
}
