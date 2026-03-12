package com.nusiss.molymarket_product_order_service.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nusiss.molymarket_product_order_service.entities.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findBySellerId(Long sellerId);
    Page<Product> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category, Pageable pageable);
    @Query(
        "SELECT p from Product p " +
        "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND " +
        "LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%'))"
    )
    Page<Product> searchProducts(
        @Param("name") String name, 
        @Param("category") String category,
        Pageable pageable
    );
}
