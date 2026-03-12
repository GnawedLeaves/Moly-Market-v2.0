package com.nusiss.molymarket_product_order_service.command;

import com.nusiss.molymarket_product_order_service.command.interfaces.Command;
import com.nusiss.molymarket_product_order_service.dtos.AddToCartDto;
import com.nusiss.molymarket_product_order_service.services.interfaces.CartService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class AddToCartCommand implements Command {
    private final CartService cartService;
    private final AddToCartDto addToCartDto;

    @Override
    public void execute() {
        cartService.addToCart(addToCartDto);
    }

    @Override
    public void undo() {
        cartService.removeFromCart(addToCartDto);
    }

}
