package com.nusiss.molymarket_product_order_service.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nusiss.molymarket_product_order_service.dtos.OrderDto;
import com.nusiss.molymarket_product_order_service.dtos.OrderItemDto;
import com.nusiss.molymarket_product_order_service.entities.Cart;
import com.nusiss.molymarket_product_order_service.entities.Order;
import com.nusiss.molymarket_product_order_service.entities.OrderItem;
import com.nusiss.molymarket_product_order_service.mappers.OrderItemMapper;
import com.nusiss.molymarket_product_order_service.mappers.OrderMapper;
import com.nusiss.molymarket_product_order_service.repositories.OrderItemRepository;
import com.nusiss.molymarket_product_order_service.repositories.OrderRepository;
import com.nusiss.molymarket_product_order_service.services.interfaces.OrderService;
import com.nusiss.molymarket_product_order_service.state.PendingState;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Override
    public Long createOrder(Cart cart) {
        Order order = new Order();
        order.setBuyerId(cart.getBuyerId());
        order.setCreatedAt(new Date());
        order.setStatus("Order Received");
        order.setTotalAmount(cart.getTotalAmount());
        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = cart.getCartItems().stream()
            .map((cartItem) -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setSellerId(cartItem.getProduct().getSellerId());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getProduct().getPrice());
                orderItem.setState(new PendingState());
                return orderItem;
            }).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
        savedOrder.getOrderItems().addAll(orderItems);
        return orderRepository.save(savedOrder).getId();
    }

    @Override
    @Transactional
    public List<OrderItemDto> getOrderItemsBySellerId(Long sellerId) {
        List<OrderItem> res = orderItemRepository.findBySellerId(sellerId);
        return OrderItemMapper.toDTOList(res);
    }

    @Override
    @Transactional
    public List<OrderDto> getOrdersBySellerId(Long sellerId) {
        List<Order> res = orderRepository.findOrdersBySellerId(sellerId);
        return OrderMapper.toDtoList(res);
    }

    @Override
    @Transactional
    public List<OrderDto> getOrdersByBuyerId(Long buyerId) {
        List<Order> res = orderRepository.findByBuyerId(buyerId);

        return OrderMapper.toDtoList(res);
    }
}
