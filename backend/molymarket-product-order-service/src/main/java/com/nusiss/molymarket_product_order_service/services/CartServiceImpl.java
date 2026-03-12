package com.nusiss.molymarket_product_order_service.services;

import com.nusiss.molymarket_product_order_service.dtos.CartDto;
import com.nusiss.molymarket_product_order_service.dtos.AddToCartDto;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nusiss.molymarket_product_order_service.entities.Cart;
import com.nusiss.molymarket_product_order_service.entities.CartItem;
import com.nusiss.molymarket_product_order_service.entities.Product;
import com.nusiss.molymarket_product_order_service.mappers.CartMapper;
import com.nusiss.molymarket_product_order_service.repositories.CartItemRepository;
import com.nusiss.molymarket_product_order_service.repositories.CartRepository;
import com.nusiss.molymarket_product_order_service.repositories.ProductRepository;
import com.nusiss.molymarket_product_order_service.services.interfaces.CartService;
import com.nusiss.molymarket_product_order_service.services.interfaces.OrderService;
import com.nusiss.molymarket_product_order_service.clients.AuthServiceClient;
import com.nusiss.molymarket_product_order_service.clients.NotificationServiceClient;
import com.nusiss.molymarket_product_order_service.clients.PaymentServiceClient;
import com.nusiss.molymarket_product_order_service.dtos.NotificationDto;
import com.nusiss.molymarket_product_order_service.dtos.RequestPaymentDto;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;
    private final CartMapper cartMapper;
    
    private final AuthServiceClient authServiceClient;
    private final NotificationServiceClient notificationServiceClient;
    private final PaymentServiceClient paymentServiceClient;

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);


    @Transactional
    @Override
    public void addToCart(AddToCartDto cartItemDto) {
        if (cartItemDto.getBuyerId() == null && cartItemDto.getUsername() != null) {
            Long buyerId = authServiceClient.getUserIdByUsername(cartItemDto.getUsername());
            cartItemDto.setBuyerId(buyerId);
        }

        Cart cart = cartRepository.findByBuyerId(cartItemDto.getBuyerId())
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setBuyerId(cartItemDto.getBuyerId());
                newCart.setCartItems(new java.util.ArrayList<>());
                newCart.setTotalAmount(0.0);
                return cartRepository.save(newCart);
            });
        Product product = productRepository.findById(cartItemDto.getProductId())
            .orElseThrow(() -> new RuntimeException("Product does not exist!"));

        logger.info("cartItemDto: {}", cartItemDto);

        if (cartItemDto.getQuantity() > product.getStock()){
            throw new RuntimeException("Insufficient stock for product ID: " + product.getId());        }
        
        CartItem existingCartItem = cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId() == cartItemDto.getProductId())
            .findFirst().orElse(null);

        logger.info("existingCartItem: {}", existingCartItem);
        if (existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemDto.getQuantity());
            cartItemRepository.save(existingCartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemDto.getQuantity());

            logger.info("cartItem: {}", cartItem);

            cart.getCartItems().add(cartItem);
        }

        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeFromCart(AddToCartDto cartItemDto) {
        if (cartItemDto.getBuyerId() == null && cartItemDto.getUsername() != null) {
            Long buyerId = authServiceClient.getUserIdByUsername(cartItemDto.getUsername());
            cartItemDto.setBuyerId(buyerId);
        }

        Cart cart = cartRepository.findByBuyerId(cartItemDto.getBuyerId())
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setBuyerId(cartItemDto.getBuyerId());
                newCart.setCartItems(new java.util.ArrayList<>());
                newCart.setTotalAmount(0.0);
                return cartRepository.save(newCart);
            });
        CartItem existingCartItem = cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId() == cartItemDto.getProductId())
            .findFirst().orElse(null);

        if (existingCartItem != null) {
            cart.getCartItems().remove(existingCartItem);
            cartItemRepository.delete(existingCartItem);
        } else {
            throw new RuntimeException("Product does not exist in the cart!");
        }
        
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void emptyCart(Long id) {
        logger.info("EMPTYCART: id {}", id);
        Cart cart = cartRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cart does not exist!"));
        cart.getCartItems().clear();
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void checkoutCart(Long id) {
        RequestPaymentDto defaultPaymentDto = new RequestPaymentDto();
        defaultPaymentDto.setAmount(0.0); // Will be updated in the overloaded method
        defaultPaymentDto.setPaymentType("CreditCard");
        defaultPaymentDto.setDetails(java.util.Map.of("cardNumber", "1234-5678-9012-3456", "cardHolder", "Test User"));
        checkoutCart(id, defaultPaymentDto);
    }

    @Transactional
    @Override
    public void checkoutCart(Long id, RequestPaymentDto paymentDto) {
        Cart cart = cartRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cart does not exist!"));

        // Process payment
        try {
            paymentDto.setAmount(cart.getTotalAmount());
            paymentServiceClient.makePayment(paymentDto);
            
            // Subtract total from buyer balance
            authServiceClient.updateBalance(cart.getBuyerId().toString(), cart.getTotalAmount(), "SUBTRACT");
        } catch (Exception e) {
            logger.error("Payment failed for cart {}: {}", id, e.getMessage());
            throw new RuntimeException("Checkout failed due to payment error: " + e.getMessage());
        }

        for (CartItem item : cart.getCartItems()){
            Product product = productRepository.findById(item.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product does not exist!"));
            
            if (item.getQuantity() > product.getStock() || product.getStock() - item.getQuantity() < 0){
                throw new RuntimeException("Insufficient stock for product ID: " + product.getId());
            }

            product.setStock(product.getStock() - item.getQuantity());
            
            // Update seller balance via Auth Service
            Double itemPrice = item.getQuantity() * product.getPrice();
            try {
                authServiceClient.updateBalance(product.getSellerId().toString(), itemPrice, "ADD");
            } catch (Exception e) {
                logger.error("Failed to update seller balance for seller {}: {}", product.getSellerId(), e.getMessage());
            }
            
            productRepository.save(product);
        }

        orderService.createOrder(cart);

        // Send notification via Notification Service
        try {
            NotificationDto notification = new NotificationDto();
            notification.setReciepientId(cart.getBuyerId());
            notification.setMessage("Order placed successfully for cart ID: " + id);
            notification.setCreatedAt(Date.from(Instant.now()));
            notificationServiceClient.createNotification(notification);
        } catch (Exception e) {
            logger.error("Failed to send notification for buyer {}: {}", cart.getBuyerId(), e.getMessage());
        }

        this.emptyCart(id);
    }

    @Transactional
    @Override
    public CartDto getCartByUsername(String username) {
        Long buyerId = authServiceClient.getUserIdByUsername(username);
        return getCartByUserId(buyerId);
    }

    @Transactional
    @Override
    public Cart getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No Cart!"));
        return cart;
    }

    

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public CartDto getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByBuyerId(userId)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setBuyerId(userId);
                newCart.setCartItems(new java.util.ArrayList<>());
                newCart.setTotalAmount(0.0);
                return cartRepository.save(newCart);
            });
        return cartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public CartDto getCartDtoById(Long id) {
        Cart cart = cartRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No Cart!"));
        return cartMapper.toDto(cart);
    }

}
