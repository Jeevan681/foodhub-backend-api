package com.jeevan.foodhub.service;

import com.jeevan.foodhub.dto.request.CreateFoodRequest;
import com.jeevan.foodhub.dto.request.UpdateFoodRequest;
import com.jeevan.foodhub.dto.response.FoodResponse;
import com.jeevan.foodhub.entity.Food;
import com.jeevan.foodhub.entity.FoodCategory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FoodService {

    FoodResponse createFood(
            Long restaurantId,
            CreateFoodRequest request
    );
    List<FoodResponse> getFoodsByRestaurant(Long restaurantId);
    FoodResponse getFoodById(Long id);
    FoodResponse updateFood(
            Long id,
            UpdateFoodRequest request
    );
    void deleteFood(Long id);
    List<FoodResponse> searchFoods(String name);
    List<FoodResponse> getFoodsByCategory(FoodCategory category);
    Page<FoodResponse> getFoods(
            int page,
            int size,
            String sortBy,
            String direction
    );

}