package com.jeevan.foodhub.mapper;

import com.jeevan.foodhub.dto.response.FavoriteResponse;
import com.jeevan.foodhub.entity.Favorite;

public class FavoriteMapper {

    private FavoriteMapper() {
    }

    public static FavoriteResponse toFavoriteResponse(
            Favorite favorite) {

        return FavoriteResponse.builder()
                .id(favorite.getId())
                .userId(favorite.getUser().getId())
                .favoriteType(favorite.getFavoriteType())

                .restaurantId(
                        favorite.getRestaurant() != null
                                ? favorite.getRestaurant().getId()
                                : null
                )

                .restaurantName(
                        favorite.getRestaurant() != null
                                ? favorite.getRestaurant().getName()
                                : null
                )

                .foodId(
                        favorite.getFood() != null
                                ? favorite.getFood().getId()
                                : null
                )

                .foodName(
                        favorite.getFood() != null
                                ? favorite.getFood().getName()
                                : null
                )

                .createdAt(favorite.getCreatedAt())
                .build();
    }
}