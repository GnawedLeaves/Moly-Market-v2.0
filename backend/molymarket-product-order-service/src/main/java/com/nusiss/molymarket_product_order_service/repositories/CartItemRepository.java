package com.nusiss.molymarket_product_order_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nusiss.molymarket_product_order_service.entities.Cart;
import com.nusiss.molymarket_product_order_service.entities.CartItem;
import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
}
