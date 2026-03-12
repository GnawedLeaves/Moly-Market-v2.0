package com.nusiss.molymarket_product_order_service.services;

import org.springframework.stereotype.Service;

import com.nusiss.molymarket_product_order_service.entities.OrderItem;
import com.nusiss.molymarket_product_order_service.repositories.OrderItemRepository;
import com.nusiss.molymarket_product_order_service.services.interfaces.OrderItemService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public void nextState(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
            .orElseThrow(() -> new RuntimeException("Order Item not found!"));
        orderItem.nextState();
        orderItemRepository.save(orderItem);
    }

    @Override
    public void cancelOrder(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
            .orElseThrow(() -> new RuntimeException("Order Item not found!"));
        orderItem.cancel();
        orderItemRepository.save(orderItem);
    }

}
