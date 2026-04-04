package com.nusiss.molymarket_product_order_service.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nusiss.molymarket_product_order_service.dtos.CartDto;
import com.nusiss.molymarket_product_order_service.command.AddToCartCommand;
import com.nusiss.molymarket_product_order_service.command.CheckoutCartCommand;
import com.nusiss.molymarket_product_order_service.command.CommandManager;
import com.nusiss.molymarket_product_order_service.command.EmptyCartCommand;
import com.nusiss.molymarket_product_order_service.command.RemoveFromCartCommand;
import com.nusiss.molymarket_product_order_service.dtos.AddToCartDto;
import com.nusiss.molymarket_product_order_service.dtos.RequestPaymentDto;
import com.nusiss.molymarket_product_order_service.services.interfaces.CartService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/product-order/cart")
public class CartController {

    @Autowired
    private CommandManager commandManager;
    @Autowired
    private CartService cartService;
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ROLE_BUYER')")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartDto addToCartDto) {
        Long cartId = addToCartDto.getCartId();
        AddToCartCommand addToCartCommand = new AddToCartCommand(cartService, addToCartDto);
        commandManager.executeCommand(cartId, addToCartCommand);
        return ResponseEntity.ok(cartService.getCartByUsername(addToCartDto.getUsername()));
    }

    @PostMapping("/remove")
    @PreAuthorize("hasAnyAuthority('ROLE_BUYER')")
    public ResponseEntity<?> removeFromCart(@RequestBody AddToCartDto addToCartDto) {
        Long userId = addToCartDto.getCartId();
        RemoveFromCartCommand removeFromCartCommand = new RemoveFromCartCommand(cartService, addToCartDto);
        commandManager.executeCommand(userId, removeFromCartCommand);
        return ResponseEntity.ok(cartService.getCartByUsername(addToCartDto.getUsername()));
    }

    @PutMapping("/empty/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_BUYER')")
    public ResponseEntity<?> emptyCart(@PathVariable Long id) {
        EmptyCartCommand emptyCartCommand = new EmptyCartCommand(cartService, id);
        commandManager.executeCommand(id, emptyCartCommand);
        return ResponseEntity.ok(cartService.getCartDtoById(id));
    }

    @PostMapping("/checkout/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_BUYER')")
    public ResponseEntity<?> checkoutCart(@PathVariable Long id, @RequestBody RequestPaymentDto paymentDto) {
        CheckoutCartCommand checkoutCartCommand = new CheckoutCartCommand(cartService, id, paymentDto);
        commandManager.executeCommand(id, checkoutCartCommand);
        return ResponseEntity.ok(cartService.getCartDtoById(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_BUYER')")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long id) {
        logger.info("USERID: {}", id);
        CartDto cartDto = cartService.getCartByUserId(id);
        return ResponseEntity.ok(cartDto);
    }
    
    @PostMapping("/undo/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_BUYER')")
    public ResponseEntity<?> undoLastOperation(@PathVariable Long id) {
        try {
            commandManager.undoCommand(id);
            return ResponseEntity.ok(cartService.getCartDtoById(id));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
