package com.nusiss.molymarket_product_order_service.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nusiss.molymarket_product_order_service.command.interfaces.Command;
import com.nusiss.molymarket_product_order_service.entities.Cart;
import com.nusiss.molymarket_product_order_service.entities.CartItem;
import com.nusiss.molymarket_product_order_service.services.interfaces.CartService;

import jakarta.transaction.Transactional;


public class EmptyCartCommand implements Command{

    private final CartService cartService;
    private final Long cartId;
    private List<CartItem> previousCartItems;
    private static final Logger logger = LoggerFactory.getLogger(EmptyCartCommand.class);

    public EmptyCartCommand(CartService cartService, Long cartId) {
        this.cartService = cartService;
        this.cartId = cartId;
    }

    @Override
    @Transactional
    public void execute() {
        Cart cart = cartService.getCartById(cartId);
        previousCartItems = new ArrayList<>(cart.getCartItems());
        cartService.emptyCart(cartId);
    }
    @Override
    @Transactional
    public void undo() {
        if (previousCartItems == null || previousCartItems.isEmpty()){
            throw new RuntimeException("No previous cart state to restore.");
        }
        logger.info("EMPTYCART UNDO NOW: cartId {}", cartId);


        Cart cart = cartService.getCartById(cartId);
        cart.getCartItems().clear();

        for (CartItem item : previousCartItems) {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(item.getProduct());
            newItem.setQuantity(item.getQuantity());
            cart.getCartItems().add(newItem);
        }

        cart.updateTotalAmount();
        cartService.saveCart(cart);
    }
}
