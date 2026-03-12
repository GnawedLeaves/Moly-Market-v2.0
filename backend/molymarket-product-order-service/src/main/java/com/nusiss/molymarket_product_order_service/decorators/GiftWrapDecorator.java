package com.nusiss.molymarket_product_order_service.decorators;

import com.nusiss.molymarket_product_order_service.decorators.interfaces.ProductComponent;

public class GiftWrapDecorator extends ProductDecorator {
    public GiftWrapDecorator(ProductComponent product) {
        super(product);
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " (Gift Wrapped)";
    }
}
