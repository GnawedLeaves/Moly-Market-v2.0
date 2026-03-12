package com.nusiss.molymarket_product_order_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nusiss.molymarket_product_order_service.dtos.ReviewDto;
import com.nusiss.molymarket_product_order_service.entities.Product;
import com.nusiss.molymarket_product_order_service.entities.Review;
import com.nusiss.molymarket_product_order_service.repositories.ProductRepository;
import com.nusiss.molymarket_product_order_service.repositories.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewServiceImpl {
    
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;

    public Review writeReview(ReviewDto review) {
        // Create a new Review entity from the DTO
        Review reviewEntity = new Review();
        Product product = productRepository.findById(review.getProduct())
            .orElseThrow(() -> new RuntimeException("Product does not exist!"));

        reviewEntity.setBuyerId(review.getBuyer());
        reviewEntity.setProduct(product);
        reviewEntity.setRating(review.getRating());
        reviewEntity.setContent(review.getContent());
        // Save the review to the database
        return reviewRepository.save(reviewEntity);
    }

    @Transactional
    public List<ReviewDto> searchReviews(Long productId) {
        // Fetch the list of reviews for a product
        System.out.println("Service Received Product ID: " + productId);
        List<Review> reviews = reviewRepository.getAllReviews();
        // Filter the reviews based on the product ID
        reviews = reviews.stream()
            .filter(review -> review.getProduct().getId() == productId)
            .toList();
        List<ReviewDto> reviewDtos = reviews.stream()
            .map(review -> new ReviewDto(
                review.getBuyerId(),
                review.getProduct().getId(),
                review.getRating(),
                review.getContent()))
            .toList();
        System.out.println("Review DTOs: " + reviewDtos);
        return reviewDtos;
    }

}
