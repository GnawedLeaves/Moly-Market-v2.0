package com.nusiss.molymarket_product_order_service.entities;

import com.nusiss.molymarket_product_order_service.decorators.interfaces.ProductComponent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements ProductComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    @Lob
    @ToString.Exclude
    private byte[] images;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;
    private Integer stock;
    private Boolean hasDiscount;
    private Double discountPercentage;
}
