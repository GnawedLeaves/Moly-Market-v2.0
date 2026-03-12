package com.nusiss.molymarket_product_order_service.services.interfaces;

import java.util.List;

import com.nusiss.molymarket_product_order_service.dtos.OrderDto;
import com.nusiss.molymarket_product_order_service.dtos.OrderItemDto;
import com.nusiss.molymarket_product_order_service.entities.Cart;

public interface OrderService {
    Long createOrder(Cart orderDto);
    
    List<OrderItemDto> getOrderItemsBySellerId(Long sellerId);

    List<OrderDto> getOrdersBySellerId(Long sellerId);

    List<OrderDto> getOrdersByBuyerId(Long buyerId);

    // List<OrderItem> getOrderItemsByBuyerId(Long buyerId);
}
