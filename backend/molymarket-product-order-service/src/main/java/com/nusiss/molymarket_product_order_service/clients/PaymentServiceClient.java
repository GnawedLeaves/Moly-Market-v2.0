package com.nusiss.molymarket_product_order_service.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.nusiss.molymarket_product_order_service.dtos.RequestPaymentDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentServiceClient {

    private final RestTemplate restTemplate;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    public void makePayment(RequestPaymentDto paymentDto) {
        String url = paymentServiceUrl + "/api/payment";
        restTemplate.postForEntity(url, paymentDto, String.class);
    }
}
