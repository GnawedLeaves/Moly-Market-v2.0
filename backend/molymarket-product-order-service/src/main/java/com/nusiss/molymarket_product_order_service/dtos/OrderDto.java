package com.nusiss.molymarket_product_order_service.dtos;

import java.util.Date;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private Long buyerId;
    private Double totalAmount;
    private String status;
    private Date createdAt;
    private List<OrderItemDto> orderItems;
}
