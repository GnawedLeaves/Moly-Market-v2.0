package com.nusiss.molymarket_product_order_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.nusiss.molymarket_product_order_service.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Fetch all reviews for a specific product
    // List<ReviewDto> findByProductId(Long productId);

    @Query(value="SELECT * FROM public.reviews", nativeQuery = true)
    List<Review> getAllReviews();

}
