package com.jeevan.foodhub.repository;

import com.jeevan.foodhub.entity.Food;
import com.jeevan.foodhub.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByRestaurantId(Long restaurantId);

    List<Food> findByCategory(FoodCategory category);

    List<Food> findByNameContainingIgnoreCase(String name);
}