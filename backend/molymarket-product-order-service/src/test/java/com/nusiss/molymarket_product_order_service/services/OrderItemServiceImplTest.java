package com.nusiss.molymarket_product_order_service.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nusiss.molymarket_product_order_service.entities.OrderItem;
import com.nusiss.molymarket_product_order_service.repositories.OrderItemRepository;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        orderItem = mock(OrderItem.class);
    }

    @Test
    void testNextState() {
        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        
        orderItemService.nextState(1L);
        
        verify(orderItem).nextState();
        verify(orderItemRepository).save(orderItem);
    }

    @Test
    void testCancelOrder() {
        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        
        orderItemService.cancelOrder(1L);
        
        verify(orderItem).cancel();
        verify(orderItemRepository).save(orderItem);
    }
}
