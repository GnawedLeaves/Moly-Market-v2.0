package com.nusiss.molymarket_product_order_service.decorators;

import com.nusiss.molymarket_product_order_service.decorators.interfaces.ProductComponent;

public class DiscountDecorator extends ProductDecorator {
    private Double discountPercentage;

    public DiscountDecorator(ProductComponent product, Double discountPercentage) {
        super(product);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public Double getPrice() {
        return product.getPrice() * (1 - (discountPercentage / 100));
    }

    @Override
    public Boolean getHasDiscount() {
        return true;
    }

    @Override
    public Double getDiscountPercentage() {
        return discountPercentage;
    }
}
