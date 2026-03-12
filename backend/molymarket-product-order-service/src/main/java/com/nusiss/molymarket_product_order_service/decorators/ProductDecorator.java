package com.nusiss.molymarket_product_order_service.decorators;

import com.nusiss.molymarket_product_order_service.decorators.interfaces.ProductComponent;

public abstract class ProductDecorator implements ProductComponent {
    protected ProductComponent product;

    public ProductDecorator(ProductComponent product) {
        this.product = product;
    }

    @Override
    public long getId() { return product.getId(); }
    @Override
    public String getName() { return product.getName(); }
    @Override
    public String getDescription() { return product.getDescription(); }
    @Override
    public Double getPrice() { return product.getPrice(); }
    @Override
    public String getCategory() { return product.getCategory(); }
    @Override
    public byte[] getImages() { return product.getImages(); }
    @Override
    public Long getSellerId() { return product.getSellerId(); }
    @Override
    public Integer getStock() { return product.getStock(); }
    @Override
    public Boolean getHasDiscount() { return product.getHasDiscount(); }
    @Override
    public Double getDiscountPercentage() { return product.getDiscountPercentage(); }
}
