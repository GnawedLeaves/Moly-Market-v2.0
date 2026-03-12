package com.nusiss.molymarket_product_order_service.controllers;

import java.util.List;

// import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nusiss.molymarket_product_order_service.dtos.OrderDto;
import com.nusiss.molymarket_product_order_service.dtos.OrderItemDto;
import com.nusiss.molymarket_product_order_service.services.interfaces.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. Get list of orders as a seller
    @GetMapping("/orders/seller/{sellerId}")
    @PreAuthorize("hasAnyAuthority('ROLE_SELLER')")
    public ResponseEntity<?> getOrdersBySellerId(@PathVariable Long sellerId) {
        try {
            List<OrderDto> orders = orderService.getOrdersBySellerId(sellerId);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // 2. Get list of order items as a seller
    @GetMapping("/order-items/seller/{sellerId}")
    @PreAuthorize("hasAnyAuthority('ROLE_SELLER')")
    public ResponseEntity<?> getOrderItemsBySellerId(@PathVariable Long sellerId) {
        try {
            List<OrderItemDto> orderItems = orderService.getOrderItemsBySellerId(sellerId);
            return new ResponseEntity<>(orderItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // 3. Get list of orders as a buyer
    @GetMapping("/orders/buyer/{buyerId}")
    @PreAuthorize("hasAnyAuthority('ROLE_BUYER')")
    public ResponseEntity<?> getOrdersByBuyerId(@PathVariable Long buyerId) {
        try {
            List<OrderDto> orders = orderService.getOrdersByBuyerId(buyerId);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // @GetMapping("/address/{buyerId}")
    // @PreAuthorize("hasAnyAuthority('ROLE_SELLER')")
    // public ResponseEntity<?> getBuyerAddress(@PathVariable Long buyerId) {
    //     String address = this.buyerService.getBuyerAddress(buyerId);
    //     if (address == null || address.isEmpty()) {
    //         address = "Buyer not found";
    //     }
    //     return new ResponseEntity<>(address, HttpStatus.OK);
    // }

    // // 4. Get list of order items as a buyer
    // @GetMapping("/order-items/buyer/{buyerId}")
    // @PreAuthorize("hasAnyAuthority('ROLE_BUYER')")
    // public ResponseEntity<?> getOrderItemsByBuyerId(@PathVariable Long buyerId) {
    //     try {
    //         List<OrderItemDto> orderItems = orderService.getOrderItemsByBuyerId(buyerId);
    //         return new ResponseEntity<>(orderItems, HttpStatus.OK);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    //     }
    // }
}
