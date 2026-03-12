package com.nusiss.molymarket_product_order_service.services.interfaces;

public interface OrderItemService {
    void nextState(Long orderItemId);
    void cancelOrder(Long orderItemId);
}
