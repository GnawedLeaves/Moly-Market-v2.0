package com.nusiss.molymarket_product_order_service.decorators.interfaces;

public interface ProductComponent {
    long getId();
    String getName();
    String getDescription();
    Double getPrice();
    String getCategory();
    byte[] getImages();
    Long getSellerId();
    Integer getStock();
    Boolean getHasDiscount();
    Double getDiscountPercentage();
}
