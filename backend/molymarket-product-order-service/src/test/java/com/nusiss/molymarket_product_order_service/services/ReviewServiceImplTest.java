package com.nusiss.molymarket_product_order_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nusiss.molymarket_product_order_service.dtos.ReviewDto;
import com.nusiss.molymarket_product_order_service.entities.Product;
import com.nusiss.molymarket_product_order_service.entities.Review;
import com.nusiss.molymarket_product_order_service.repositories.ProductRepository;
import com.nusiss.molymarket_product_order_service.repositories.ReviewRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Product product;
    private Review review;
    private ReviewDto reviewDto;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);

        review = new Review();
        review.setId(1L);
        review.setProduct(product);
        review.setBuyerId(1L);
        review.setRating(5);
        review.setContent("Great!");

        reviewDto = new ReviewDto();
        reviewDto.setProduct(1L);
        reviewDto.setBuyer(1L);
        reviewDto.setRating(5);
        reviewDto.setContent("Great!");
    }

    @Test
    void testWriteReview() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        
        Review result = reviewService.writeReview(reviewDto);
        assertNotNull(result);
        assertEquals(5, result.getRating());
    }

    @Test
    void testSearchReviews() {
        when(reviewRepository.getAllReviews()).thenReturn(Collections.singletonList(review));
        
        List<ReviewDto> result = reviewService.searchReviews(1L);
        assertEquals(1, result.size());
        assertEquals("Great!", result.get(0).getContent());
    }
}
