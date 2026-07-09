package com.jeevan.foodhub.mapper;

import com.jeevan.foodhub.dto.response.FoodResponse;
import com.jeevan.foodhub.entity.Food;

public class FoodMapper {

    private FoodMapper() {
    }

    public static FoodResponse toResponse(Food food) {

        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .description(food.getDescription())
                .price(food.getPrice())
                .imageUrl(food.getImageUrl())
                .available(food.getAvailable())
                .category(food.getCategory())
                .restaurantId(food.getRestaurant().getId())
                .restaurantName(food.getRestaurant().getName())
                .build();
    }
}