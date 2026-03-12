package com.nusiss.molymarket_product_order_service.mappers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.nusiss.molymarket_product_order_service.dtos.CreateProductDto;
import com.nusiss.molymarket_product_order_service.dtos.ProductDto;
import com.nusiss.molymarket_product_order_service.entities.Product;
import com.nusiss.molymarket_product_order_service.decorators.DiscountDecorator;
import com.nusiss.molymarket_product_order_service.decorators.GiftWrapDecorator;
import com.nusiss.molymarket_product_order_service.decorators.interfaces.ProductComponent;

@Component
public class ProductMapper {
    public Product toEntity(CreateProductDto productDto) throws IOException{
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        if (productDto.getImageFile() != null) {
            product.setImages(productDto.getImageFile().getBytes());
        }
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setSellerId(productDto.getSellerId());
        product.setHasDiscount(productDto.getHasDiscount());
        product.setDiscountPercentage(productDto.getDiscountPercentage());
        return product;
    }

    public ProductDto toDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setImages(product.getImages());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setSellerId(product.getSellerId());     
        productDto.setHasDiscount(product.getHasDiscount());   
        productDto.setDiscountPercentage(product.getDiscountPercentage());
        return productDto;
    }

    public ProductDto toDto(ProductComponent productComponent) {
        ProductDto productDto = new ProductDto();
        
        productDto.setId(productComponent.getId());
        productDto.setName(productComponent.getName());
        productDto.setDescription(productComponent.getDescription());
        productDto.setPrice(productComponent.getPrice());
        productDto.setCategory(productComponent.getCategory());
        productDto.setStock(productComponent.getStock());
        productDto.setImages(productComponent.getImages());
        productDto.setHasDiscount(productComponent.getHasDiscount());
        productDto.setDiscountPercentage(productComponent.getDiscountPercentage());

        if (productComponent instanceof DiscountDecorator) {
            // The price in DiscountDecorator already includes the discount, so no need to adjust here
            // If you want to show the discount percentage or the discounted price, you can set that too
            DiscountDecorator discountDecorator = (DiscountDecorator) productComponent;
            // Optionally, store the original price or discount percentage
            productDto.setPrice(discountDecorator.getPrice());  // The price includes the discount
        }
        
        if (productComponent instanceof GiftWrapDecorator) {
            // The GiftWrapDecorator modifies the description; we need to add it
            GiftWrapDecorator giftWrapDecorator = (GiftWrapDecorator) productComponent;
            productDto.setDescription(giftWrapDecorator.getDescription());  // Updated description with gift wrap info
        }
    
        return productDto;
    }
}
