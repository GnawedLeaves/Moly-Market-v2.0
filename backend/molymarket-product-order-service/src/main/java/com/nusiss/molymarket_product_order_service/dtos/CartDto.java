package com.nusiss.molymarket_product_order_service.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;
    private Long buyerId;
    private double totalAmount;
    private List<CartItemDto> items;
}
