package com.nusiss.molymarket_product_order_service.state;

import com.nusiss.molymarket_product_order_service.entities.OrderItem;
import com.nusiss.molymarket_product_order_service.state.interfaces.OrderItemState;

public class PendingState implements OrderItemState {

    @Override
    public void next(OrderItem orderItem) {
        orderItem.setState(new ShippingState());
    }

    @Override
    public void cancel(OrderItem orderItem) {
        orderItem.setState(new CancelledState());
    }

    @Override
    public String getStatus() {
        return "PENDING";
    }

}
