package com.nusiss.molymarket_product_order_service.services.interfaces;

import com.nusiss.molymarket_product_order_service.dtos.CartDto;
import com.nusiss.molymarket_product_order_service.entities.Cart;
import com.nusiss.molymarket_product_order_service.dtos.AddToCartDto;
import com.nusiss.molymarket_product_order_service.dtos.RequestPaymentDto;

public interface CartService {
    CartDto getCartByUsername(String username);

    Cart getCartById(Long id);

    CartDto getCartDtoById(Long id);

    CartDto getCartByUserId(Long userId);
    
    void addToCart(AddToCartDto cartDto);

    void removeFromCart(AddToCartDto cartItemDto);

    void emptyCart(Long id);

    void checkoutCart(Long id);

    void checkoutCart(Long id, RequestPaymentDto paymentDto);

    void saveCart(Cart cart);
}
