package com.nusiss.molymarket_product_order_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nusiss.molymarket_product_order_service.clients.AuthServiceClient;
import com.nusiss.molymarket_product_order_service.dtos.CreateProductDto;
import com.nusiss.molymarket_product_order_service.dtos.ProductDto;
import com.nusiss.molymarket_product_order_service.entities.Product;
import com.nusiss.molymarket_product_order_service.mappers.ProductMapper;
import com.nusiss.molymarket_product_order_service.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private AuthServiceClient authServiceClient;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private CreateProductDto createProductDto;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setSellerId(null); // Set to null for createProduct test

        createProductDto = new CreateProductDto();
        createProductDto.setName("Test Product");
        createProductDto.setUsername("seller");

        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Test Product");
    }

    @Test
    void testCreateProduct() throws IOException {
        when(productMapper.toEntity(any(CreateProductDto.class))).thenReturn(product);
        when(authServiceClient.getUserIdByUsername("seller")).thenReturn(1L);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        Long id = productService.createProduct(createProductDto);
        assertEquals(1L, id);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testGetProductById() {
        product.setSellerId(1L); // Restore sellerId
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(any(com.nusiss.molymarket_product_order_service.decorators.interfaces.ProductComponent.class))).thenReturn(productDto);
        
        ProductDto result = productService.getProductById(1L, false, 0.0, false);
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(productMapper.toDto(any())).thenReturn(productDto);
        
        List<ProductDto> result = productService.getAllProducts();
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateProduct() throws IOException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        Long id = productService.updateProduct(createProductDto, 1L);
        assertEquals(1L, id);
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.deleteProduct(1L);
        verify(productRepository).delete(product);
    }
}
