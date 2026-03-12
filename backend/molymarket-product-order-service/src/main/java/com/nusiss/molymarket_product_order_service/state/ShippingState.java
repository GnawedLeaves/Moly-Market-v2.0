package com.nusiss.molymarket_product_order_service.state;

import com.nusiss.molymarket_product_order_service.entities.OrderItem;
import com.nusiss.molymarket_product_order_service.state.interfaces.OrderItemState;

public class ShippingState implements OrderItemState {

    @Override
    public void next(OrderItem orderItem) {
        orderItem.setState(new DeliveredState());
    }

    @Override
    public void cancel(OrderItem orderItem) {
        orderItem.setState(new CancelledState());
    }

    @Override
    public String getStatus() {
        return "SHIPPING";
    }

}
