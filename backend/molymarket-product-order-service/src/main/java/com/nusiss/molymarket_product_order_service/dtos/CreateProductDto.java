package com.nusiss.molymarket_product_order_service.dtos;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    private String name;
    private String description;
    private Double price;
    private String category;
    private MultipartFile imageFile;
    private Long sellerId;
    private Integer stock;
    private Boolean hasDiscount;
    private Double discountPercentage;
    private String username;
}
