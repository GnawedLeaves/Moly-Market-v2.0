package com.nusiss.molymarket_product_order_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nusiss.molymarket_product_order_service.services.interfaces.OrderItemService;

@RestController
@RequestMapping("/api/product-order/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/{id}/next")
    public ResponseEntity<String> nextState(@PathVariable Long id) {
        orderItemService.nextState(id);
        return ResponseEntity.ok("Order item moved to next state");
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        orderItemService.cancelOrder(id);
        return ResponseEntity.ok("Order item cancelled");
    }
}
