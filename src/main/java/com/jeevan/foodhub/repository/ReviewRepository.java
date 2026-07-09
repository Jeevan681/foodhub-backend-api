package com.jeevan.foodhub.repository;

import com.jeevan.foodhub.entity.Food;
import com.jeevan.foodhub.entity.Restaurant;
import com.jeevan.foodhub.entity.Review;
import com.jeevan.foodhub.entity.ReviewType;
import com.jeevan.foodhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByRestaurantOrderByCreatedAtDesc(Restaurant restaurant);

    List<Review> findByFoodOrderByCreatedAtDesc(Food food);

    List<Review> findByUserOrderByCreatedAtDesc(User user);

    Optional<Review> findByUserAndRestaurant(User user, Restaurant restaurant);

    Optional<Review> findByUserAndFood(User user, Food food);

    long countByRestaurant(Restaurant restaurant);

    long countByFood(Food food);

    @Query("""
            SELECT AVG(r.rating)
            FROM Review r
            WHERE r.restaurant = :restaurant
            """)
    Double getRestaurantAverageRating(Restaurant restaurant);

    @Query("""
            SELECT AVG(r.rating)
            FROM Review r
            WHERE r.food = :food
            """)
    Double getFoodAverageRating(Food food);
}