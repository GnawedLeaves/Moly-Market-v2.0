package com.nusiss.molymarket_product_order_service.dtos;

import java.util.Map;

import lombok.Data;

@Data
public class RequestPaymentDto {
    private String paymentType;
    private double amount;
    private Map<String, String> details;
}
