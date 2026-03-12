package com.nusiss.molymarket_product_order_service.state;

import com.nusiss.molymarket_product_order_service.entities.OrderItem;
import com.nusiss.molymarket_product_order_service.state.interfaces.OrderItemState;

public class DeliveredState implements OrderItemState {

    @Override
    public void next(OrderItem orderItem) {
        throw new IllegalStateException("Order already delivered.");
    }

    @Override
    public void cancel(OrderItem orderItem) {
        throw new IllegalStateException("Cannot cancel a delivered order.");
    }

    @Override
    public String getStatus() {
        return "DELIVERED";
    }

}
