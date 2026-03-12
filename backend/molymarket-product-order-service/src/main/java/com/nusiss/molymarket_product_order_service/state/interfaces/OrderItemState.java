package com.nusiss.molymarket_product_order_service.state.interfaces;

import com.nusiss.molymarket_product_order_service.entities.OrderItem;

public interface OrderItemState {
    void next(OrderItem orderItem);
    void cancel(OrderItem orderItem);
    String getStatus();
}
