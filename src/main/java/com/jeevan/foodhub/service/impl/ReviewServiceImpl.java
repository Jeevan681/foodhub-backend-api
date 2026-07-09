package com.jeevan.foodhub.service.impl;

import com.jeevan.foodhub.dto.request.CreateReviewRequest;
import com.jeevan.foodhub.dto.request.UpdateReviewRequest;
import com.jeevan.foodhub.dto.response.ReviewResponse;
import com.jeevan.foodhub.dto.response.ReviewSummaryResponse;
import com.jeevan.foodhub.entity.*;
import com.jeevan.foodhub.exception.ResourceNotFoundException;
import com.jeevan.foodhub.mapper.ReviewMapper;
import com.jeevan.foodhub.repository.*;
import com.jeevan.foodhub.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            FoodRepository foodRepository,
            ReviewRepository reviewRepository) {

        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewResponse createRestaurantReview(
            String userEmail,
            Long restaurantId,
            CreateReviewRequest request) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));

        reviewRepository.findByUserAndRestaurant(user, restaurant)
                .ifPresent(review -> {
                    throw new RuntimeException(
                            "You have already reviewed this restaurant"
                    );
                });

        Review review = Review.builder()
                .rating(request.getRating())
                .comment(request.getComment())
                .reviewType(ReviewType.RESTAURANT)
                .user(user)
                .restaurant(restaurant)
                .build();

        return ReviewMapper.toReviewResponse(
                reviewRepository.save(review)
        );
    }
    @Override
    public ReviewResponse createFoodReview(
            String userEmail,
            Long foodId,
            CreateReviewRequest request) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found"));

        reviewRepository.findByUserAndFood(user, food)
                .ifPresent(review -> {
                    throw new RuntimeException(
                            "You have already reviewed this food"
                    );
                });

        Review review = Review.builder()
                .rating(request.getRating())
                .comment(request.getComment())
                .reviewType(ReviewType.FOOD)
                .user(user)
                .food(food)
                .build();

        return ReviewMapper.toReviewResponse(
                reviewRepository.save(review)
        );
    }
    @Override
    public List<ReviewResponse> getRestaurantReviews(Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));

        return reviewRepository
                .findByRestaurantOrderByCreatedAtDesc(restaurant)
                .stream()
                .map(ReviewMapper::toReviewResponse)
                .toList();
    }
    @Override
    public List<ReviewResponse> getFoodReviews(Long foodId) {

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found"));

        return reviewRepository
                .findByFoodOrderByCreatedAtDesc(food)
                .stream()
                .map(ReviewMapper::toReviewResponse)
                .toList();
    }
    @Override
    public List<ReviewResponse> getMyReviews(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return reviewRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(ReviewMapper::toReviewResponse)
                .toList();
    }
    @Override
    public ReviewResponse updateReview(
            String userEmail,
            Long reviewId,
            UpdateReviewRequest request) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "Review does not belong to this user"
            );
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return ReviewMapper.toReviewResponse(
                reviewRepository.save(review)
        );
    }
    @Override
    public void deleteReview(
            String userEmail,
            Long reviewId) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "Review does not belong to this user"
            );
        }

        reviewRepository.delete(review);
    }
    @Override
    public ReviewSummaryResponse getRestaurantReviewSummary(
            Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));

        Double average =
                reviewRepository.getRestaurantAverageRating(restaurant);

        Long count =
                reviewRepository.countByRestaurant(restaurant);

        return ReviewSummaryResponse.builder()
                .averageRating(
                        average == null ? 0.0 : average
                )
                .reviewCount(count)
                .build();
    }

    @Override
    public ReviewSummaryResponse getFoodReviewSummary(
            Long foodId) {

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found"));

        Double average =
                reviewRepository.getFoodAverageRating(food);

        Long count =
                reviewRepository.countByFood(food);

        return ReviewSummaryResponse.builder()
                .averageRating(
                        average == null ? 0.0 : average
                )
                .reviewCount(count)
                .build();
    }

}