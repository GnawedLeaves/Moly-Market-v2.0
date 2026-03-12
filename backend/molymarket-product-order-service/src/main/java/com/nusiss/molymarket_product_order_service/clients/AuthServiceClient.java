package com.nusiss.molymarket_product_order_service.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthServiceClient {

    private final RestTemplate restTemplate;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    public void updateBalance(String userId, Double amount, String operation) {
        String url = UriComponentsBuilder.fromUriString(authServiceUrl)
                .path("/auth/updateBalanceById")
                .queryParam("userId", userId)
                .queryParam("amount", amount)
                .queryParam("operation", operation)
                .toUriString();

        restTemplate.postForEntity(url, null, String.class);
    }

    public Long getUserIdByUsername(String username) {
        String url = UriComponentsBuilder.fromUriString(authServiceUrl)
                .path("/auth/getUserId")
                .queryParam("username", username)
                .toUriString();

        return restTemplate.getForObject(url, Long.class);
    }
}
