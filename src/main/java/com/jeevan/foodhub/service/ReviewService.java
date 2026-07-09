package com.jeevan.foodhub.service;

import com.jeevan.foodhub.dto.request.CreateReviewRequest;
import com.jeevan.foodhub.dto.request.UpdateReviewRequest;
import com.jeevan.foodhub.dto.response.ReviewResponse;
import com.jeevan.foodhub.dto.response.ReviewSummaryResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse createRestaurantReview(
            String userEmail,
            Long restaurantId,
            CreateReviewRequest request
    );

    ReviewResponse createFoodReview(
            String userEmail,
            Long foodId,
            CreateReviewRequest request
    );

    List<ReviewResponse> getRestaurantReviews(Long restaurantId);

    List<ReviewResponse> getFoodReviews(Long foodId);

    List<ReviewResponse> getMyReviews(String userEmail);

    ReviewResponse updateReview(
            String userEmail,
            Long reviewId,
            UpdateReviewRequest request
    );

    void deleteReview(
            String userEmail,
            Long reviewId
    );
    ReviewSummaryResponse getRestaurantReviewSummary(
            Long restaurantId
    );

    ReviewSummaryResponse getFoodReviewSummary(
            Long foodId
    );
}