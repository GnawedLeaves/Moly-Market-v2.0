package com.nusiss.molymarket_product_order_service.command;

import com.nusiss.molymarket_product_order_service.command.interfaces.Command;
import com.nusiss.molymarket_product_order_service.dtos.AddToCartDto;
import com.nusiss.molymarket_product_order_service.services.interfaces.CartService;

public class RemoveFromCartCommand implements Command {
    private final CartService cartService;
    private final AddToCartDto addToCartDto;

    public RemoveFromCartCommand(CartService cartService, AddToCartDto addToCartDto){
        this.addToCartDto = addToCartDto;
        this.cartService = cartService;
    }

    @Override
    public void execute() {
        cartService.removeFromCart(addToCartDto);
    }
    @Override
    public void undo() {
        cartService.addToCart(addToCartDto);
    }

}
