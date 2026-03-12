package com.nusiss.molymarket_product_order_service.dtos;


import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    @Lob
    private byte[] images;
    private Long sellerId;
    private Integer stock;
    private Boolean hasDiscount;
    private Double discountPercentage;
}
