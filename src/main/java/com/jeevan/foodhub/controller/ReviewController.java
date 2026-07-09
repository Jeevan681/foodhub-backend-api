package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.request.CreateReviewRequest;
import com.jeevan.foodhub.dto.request.UpdateReviewRequest;
import com.jeevan.foodhub.dto.response.ReviewResponse;
import com.jeevan.foodhub.dto.response.ReviewSummaryResponse;
import com.jeevan.foodhub.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/api/restaurants/{restaurantId}/reviews")
    public ResponseEntity<ReviewResponse> createRestaurantReview(

            Authentication authentication,

            @PathVariable Long restaurantId,

            @Valid
            @RequestBody
            CreateReviewRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        reviewService.createRestaurantReview(
                                authentication.getName(),
                                restaurantId,
                                request
                        )
                );
    }

    @PostMapping("/api/foods/{foodId}/reviews")
    public ResponseEntity<ReviewResponse> createFoodReview(

            Authentication authentication,

            @PathVariable Long foodId,

            @Valid
            @RequestBody
            CreateReviewRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        reviewService.createFoodReview(
                                authentication.getName(),
                                foodId,
                                request
                        )
                );
    }

    @GetMapping("/api/restaurants/{restaurantId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getRestaurantReviews(

            @PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                reviewService.getRestaurantReviews(restaurantId)
        );
    }

    @GetMapping("/api/foods/{foodId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getFoodReviews(

            @PathVariable Long foodId) {

        return ResponseEntity.ok(
                reviewService.getFoodReviews(foodId)
        );
    }

    @GetMapping("/api/reviews/me")
    public ResponseEntity<List<ReviewResponse>> getMyReviews(

            Authentication authentication) {

        return ResponseEntity.ok(
                reviewService.getMyReviews(
                        authentication.getName()
                )
        );
    }

    @PutMapping("/api/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(

            Authentication authentication,

            @PathVariable Long reviewId,

            @Valid
            @RequestBody
            UpdateReviewRequest request) {

        return ResponseEntity.ok(
                reviewService.updateReview(
                        authentication.getName(),
                        reviewId,
                        request
                )
        );
    }

    @DeleteMapping("/api/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(

            Authentication authentication,

            @PathVariable Long reviewId) {

        reviewService.deleteReview(
                authentication.getName(),
                reviewId
        );

        return ResponseEntity.ok(
                "Review deleted successfully"
        );
    }
    @GetMapping("/api/restaurants/{restaurantId}/reviews/summary")
    public ResponseEntity<ReviewSummaryResponse> getRestaurantSummary(

            @PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                reviewService.getRestaurantReviewSummary(
                        restaurantId
                )
        );
    }
    @GetMapping("/api/foods/{foodId}/reviews/summary")
    public ResponseEntity<ReviewSummaryResponse> getFoodSummary(

            @PathVariable Long foodId) {

        return ResponseEntity.ok(
                reviewService.getFoodReviewSummary(
                        foodId
                )
        );
    }
}