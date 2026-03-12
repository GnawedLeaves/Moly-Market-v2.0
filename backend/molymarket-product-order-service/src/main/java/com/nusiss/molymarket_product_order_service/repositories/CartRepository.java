package com.nusiss.molymarket_product_order_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nusiss.molymarket_product_order_service.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByBuyerId(Long buyerId);
}
