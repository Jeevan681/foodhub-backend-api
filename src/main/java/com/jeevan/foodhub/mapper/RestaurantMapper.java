package com.jeevan.foodhub.mapper;

import com.jeevan.foodhub.dto.response.RestaurantResponse;
import com.jeevan.foodhub.dto.response.RestaurantSummaryResponse;
import com.jeevan.foodhub.entity.Restaurant;

public class RestaurantMapper {

    private RestaurantMapper() {
    }

    public static RestaurantSummaryResponse toSummary(Restaurant restaurant) {
        return RestaurantSummaryResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .phone(restaurant.getPhone())
                .isOpen(restaurant.getIsOpen())
                .totalFoods(restaurant.getFoods().size())
                .build();
    }
    public static RestaurantResponse toResponse(Restaurant restaurant) {

        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .address(restaurant.getAddress())
                .phone(restaurant.getPhone())
                .imageUrl(restaurant.getImageUrl())
                .isOpen(restaurant.getIsOpen())
                .build();
    }
}