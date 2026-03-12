package com.nusiss.molymarket_product_order_service.state;

import com.nusiss.molymarket_product_order_service.entities.OrderItem;
import com.nusiss.molymarket_product_order_service.state.interfaces.OrderItemState;

public class CancelledState implements OrderItemState {

    @Override
    public void next(OrderItem orderItem) {
        throw new IllegalStateException("Cannot move to next state from cancelled state.");
    }

    @Override
    public void cancel(OrderItem orderItem) {
        throw new IllegalStateException("Order already cancelled.");
    }

    @Override
    public String getStatus() {
        return "CANCELLED";
    }

}
