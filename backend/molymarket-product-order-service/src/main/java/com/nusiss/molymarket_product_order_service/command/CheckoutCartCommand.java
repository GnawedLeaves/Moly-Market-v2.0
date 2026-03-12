package com.nusiss.molymarket_product_order_service.command;

import com.nusiss.molymarket_product_order_service.command.interfaces.Command;
import com.nusiss.molymarket_product_order_service.dtos.RequestPaymentDto;
import com.nusiss.molymarket_product_order_service.services.interfaces.CartService;


public class CheckoutCartCommand implements Command {
    private final CartService cartService;
    private final Long cartId;
    private RequestPaymentDto paymentDto;

    public CheckoutCartCommand(CartService cartService, Long cartId) {
        this.cartService = cartService;
        this.cartId = cartId;
    }

    public CheckoutCartCommand(CartService cartService, Long cartId, RequestPaymentDto paymentDto) {
        this.cartService = cartService;
        this.cartId = cartId;
        this.paymentDto = paymentDto;
    }

    @Override
    public void execute() {
        if (paymentDto != null) {
            cartService.checkoutCart(cartId, paymentDto);
        } else {
            cartService.checkoutCart(cartId);
        }
    }
    @Override
    public void undo() {
        throw new UnsupportedOperationException("Cannot undo checkout operation");    
    }
    
}
