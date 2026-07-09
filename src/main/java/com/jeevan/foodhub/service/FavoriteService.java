package com.jeevan.foodhub.service;

import com.jeevan.foodhub.dto.response.FavoriteResponse;

import java.util.List;

public interface FavoriteService {

    FavoriteResponse addRestaurantFavorite(
            String userEmail,
            Long restaurantId
    );

    FavoriteResponse addFoodFavorite(
            String userEmail,
            Long foodId
    );

    List<FavoriteResponse> getFavoriteRestaurants(
            String userEmail
    );

    List<FavoriteResponse> getFavoriteFoods(
            String userEmail
    );

    void removeRestaurantFavorite(
            String userEmail,
            Long restaurantId
    );

    void removeFoodFavorite(
            String userEmail,
            Long foodId
    );
}