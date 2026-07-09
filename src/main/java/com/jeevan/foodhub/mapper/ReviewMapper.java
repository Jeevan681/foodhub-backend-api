package com.jeevan.foodhub.mapper;

import com.jeevan.foodhub.dto.response.ReviewResponse;
import com.jeevan.foodhub.entity.Review;

public class ReviewMapper {

    private ReviewMapper() {
    }

    public static ReviewResponse toReviewResponse(Review review) {

        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .userName(review.getUser().getName())
                .rating(review.getRating())
                .comment(review.getComment())
                .reviewType(review.getReviewType())

                .restaurantId(
                        review.getRestaurant() != null
                                ? review.getRestaurant().getId()
                                : null
                )

                .restaurantName(
                        review.getRestaurant() != null
                                ? review.getRestaurant().getName()
                                : null
                )

                .foodId(
                        review.getFood() != null
                                ? review.getFood().getId()
                                : null
                )

                .foodName(
                        review.getFood() != null
                                ? review.getFood().getName()
                                : null
                )

                .createdAt(review.getCreatedAt())
                .build();
    }
}