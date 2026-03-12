package com.nusiss.molymarket_product_order_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nusiss.molymarket_product_order_service.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT o FROM Order o JOIN o.orderItems oi WHERE oi.sellerId = :sellerId")
    List<Order> findOrdersBySellerId(@Param("sellerId") Long sellerId);

    List<Order> findByBuyerId(Long id);
}
