package com.nusiss.molymarket_product_order_service.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nusiss.molymarket_product_order_service.dtos.CreateProductDto;
import com.nusiss.molymarket_product_order_service.dtos.ProductDto;
import com.nusiss.molymarket_product_order_service.entities.Product;
import com.nusiss.molymarket_product_order_service.mappers.ProductMapper;
import com.nusiss.molymarket_product_order_service.repositories.ProductRepository;
import com.nusiss.molymarket_product_order_service.services.interfaces.ProductService;
import com.nusiss.molymarket_product_order_service.decorators.DiscountDecorator;
import com.nusiss.molymarket_product_order_service.decorators.GiftWrapDecorator;
import com.nusiss.molymarket_product_order_service.decorators.interfaces.ProductComponent;
import com.nusiss.molymarket_product_order_service.clients.AuthServiceClient;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuthServiceClient authServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Override
    public Long createProduct(CreateProductDto productDto) throws IOException {
        Product product = productMapper.toEntity(productDto);
        
        if (productDto.getUsername() != null && product.getSellerId() == null) {
            Long sellerId = authServiceClient.getUserIdByUsername(productDto.getUsername());
            product.setSellerId(sellerId);
        }
        
        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    @Override
    public ProductDto getProductById(Long id, Boolean discount, Double discountPercentage, Boolean giftWrap) {

        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product with Id: " + id + " not found!"));        

        ProductComponent decoratedProduct = product;

        if (giftWrap) {
            decoratedProduct = new GiftWrapDecorator(decoratedProduct);
        }
        if (discount) {
            decoratedProduct = new DiscountDecorator(decoratedProduct, discountPercentage);
        }
        
        return productMapper.toDto(decoratedProduct);
    }

    @Override
    @Transactional
    public List<ProductDto> getProductsBySellerId(Long sellerId) {
        List<Product> products = productRepository.findBySellerId(sellerId);
        
        return products.stream().map(productMapper::toDto).toList();
    }

    @Override
    public Long updateProduct(CreateProductDto productDto, Long id) throws IOException {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product with ID" + id + " does not exist!"));
        
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        if (productDto.getImageFile() != null){
            product.setImages(productDto.getImageFile().getBytes());
        }
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setHasDiscount(productDto.getHasDiscount());
        product.setDiscountPercentage(productDto.getDiscountPercentage());
        
        return productRepository.save(product).getId();
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product with Id: " + id + " not found!"));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDto).toList();
    }

    @Override
    @Transactional
    public Page<ProductDto> searchProduct(String name, String category, int page, int size) {
        logger.info("Searching with name: {}, category: {}, page: {}, size: {}", name, category, page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Product> productPage = productRepository.searchProducts(name, category, pageable);
        logger.info("Product Page Content: {}", productPage);

        List<ProductDto> productDtos = productPage.getContent().stream()
            .map(productMapper::toDto)
            .collect(Collectors.toList());

        return new PageImpl<>(productDtos, pageable, productPage.getTotalElements());
    }

}
