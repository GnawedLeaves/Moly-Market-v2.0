package com.nusiss.molymarket_product_order_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nusiss.molymarket_product_order_service.dtos.OrderDto;
import com.nusiss.molymarket_product_order_service.entities.Cart;
import com.nusiss.molymarket_product_order_service.entities.Order;
import com.nusiss.molymarket_product_order_service.repositories.OrderItemRepository;
import com.nusiss.molymarket_product_order_service.repositories.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Cart cart;
    private Order order;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setBuyerId(1L);
        cart.setTotalAmount(100.0);
        cart.setCartItems(new ArrayList<>());

        order = new Order();
        order.setId(1L);
        order.setBuyerId(1L);
        order.setTotalAmount(100.0);
        order.setOrderItems(new ArrayList<>());
    }

    @Test
    void testCreateOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        
        Long id = orderService.createOrder(cart);
        assertEquals(1L, id);
        // orderRepository.save is called twice in createOrder
    }

    @Test
    void testGetOrdersByBuyerId() {
        when(orderRepository.findByBuyerId(1L)).thenReturn(Collections.singletonList(order));
        
        List<OrderDto> result = orderService.getOrdersByBuyerId(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
