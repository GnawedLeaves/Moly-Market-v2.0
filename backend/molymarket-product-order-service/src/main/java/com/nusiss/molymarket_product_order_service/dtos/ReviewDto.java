package com.nusiss.molymarket_product_order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long buyer;
    private Long product;
    private int rating;
    private String content;
}
