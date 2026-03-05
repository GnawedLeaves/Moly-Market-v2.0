package com.nusiss.molymarket_payment_service.dtos;

import java.util.Map;

import lombok.Data;

@Data
public class RequestPaymentDto {
    private String paymentType;
    private double amount;
    private Map<String, String> details;
}