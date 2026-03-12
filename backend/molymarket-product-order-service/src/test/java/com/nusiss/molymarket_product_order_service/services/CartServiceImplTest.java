package com.nusiss.molymarket_product_order_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nusiss.molymarket_product_order_service.clients.AuthServiceClient;
import com.nusiss.molymarket_product_order_service.clients.NotificationServiceClient;
import com.nusiss.molymarket_product_order_service.clients.PaymentServiceClient;
import com.nusiss.molymarket_product_order_service.dtos.AddToCartDto;
import com.nusiss.molymarket_product_order_service.dtos.CartDto;
import com.nusiss.molymarket_product_order_service.entities.Cart;
import com.nusiss.molymarket_product_order_service.entities.Product;
import com.nusiss.molymarket_product_order_service.mappers.CartMapper;
import com.nusiss.molymarket_product_order_service.repositories.CartItemRepository;
import com.nusiss.molymarket_product_order_service.repositories.CartRepository;
import com.nusiss.molymarket_product_order_service.repositories.ProductRepository;
import com.nusiss.molymarket_product_order_service.services.interfaces.OrderService;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private CartMapper cartMapper;

    @Mock
    private AuthServiceClient authServiceClient;

    @Mock
    private NotificationServiceClient notificationServiceClient;

    @Mock
    private PaymentServiceClient paymentServiceClient;

    @InjectMocks
    private CartServiceImpl cartService;

    private Cart cart;
    private Product product;
    private AddToCartDto addToCartDto;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setId(1L);
        cart.setBuyerId(1L);
        cart.setCartItems(new ArrayList<>());
        cart.setTotalAmount(100.0);

        product = new Product();
        product.setId(1L);
        product.setStock(10);
        product.setPrice(100.0);
        product.setHasDiscount(false);
        product.setDiscountPercentage(0.0);

        addToCartDto = new AddToCartDto();
        addToCartDto.setBuyerId(1L);
        addToCartDto.setProductId(1L);
        addToCartDto.setQuantity(2);
    }

    @Test
    void testAddToCart() {
        when(cartRepository.findByBuyerId(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        
        cartService.addToCart(addToCartDto);
        
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void testAddToCartInsufficientStock() {
        addToCartDto.setQuantity(20);
        when(cartRepository.findByBuyerId(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        
        assertThrows(RuntimeException.class, () -> cartService.addToCart(addToCartDto));
    }

    @Test
    void testEmptyCart() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        cartService.emptyCart(1L);
        verify(cartRepository).save(cart);
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    void testGetCartByUserId() {
        when(cartRepository.findByBuyerId(1L)).thenReturn(Optional.of(cart));
        when(cartMapper.toDto(any(Cart.class))).thenReturn(new CartDto());
        
        CartDto result = cartService.getCartByUserId(1L);
        assertNotNull(result);
    }
}
