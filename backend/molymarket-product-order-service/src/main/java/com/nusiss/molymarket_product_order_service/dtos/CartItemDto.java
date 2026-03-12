package com.nusiss.molymarket_product_order_service.dtos;


import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Boolean hasDiscount;
    private Double discountPercentage;    
    private String category;
    @Lob
    private byte[] images;
    private Long sellerId;
    private Integer quantity;
}
