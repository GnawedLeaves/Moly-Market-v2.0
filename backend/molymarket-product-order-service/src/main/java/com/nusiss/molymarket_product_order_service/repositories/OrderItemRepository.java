package com.nusiss.molymarket_product_order_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nusiss.molymarket_product_order_service.entities.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findBySellerId(Long sellerId);
}
