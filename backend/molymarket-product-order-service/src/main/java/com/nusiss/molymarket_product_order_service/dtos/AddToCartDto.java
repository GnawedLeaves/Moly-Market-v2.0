package com.nusiss.molymarket_product_order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {
    private Long cartId;
    private String username;
    private Long buyerId;
    private Long productId;
    private int quantity;
}
