package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.response.FavoriteResponse;
import com.jeevan.foodhub.service.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/api/restaurants/{restaurantId}/favorite")
    public ResponseEntity<FavoriteResponse> addRestaurantFavorite(

            Authentication authentication,

            @PathVariable Long restaurantId) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        favoriteService.addRestaurantFavorite(
                                authentication.getName(),
                                restaurantId
                        )
                );
    }

    @DeleteMapping("/api/restaurants/{restaurantId}/favorite")
    public ResponseEntity<String> removeRestaurantFavorite(

            Authentication authentication,

            @PathVariable Long restaurantId) {

        favoriteService.removeRestaurantFavorite(
                authentication.getName(),
                restaurantId
        );

        return ResponseEntity.ok(
                "Restaurant removed from favorites"
        );
    }

    @GetMapping("/api/restaurants/favorites")
    public ResponseEntity<List<FavoriteResponse>> getRestaurantFavorites(

            Authentication authentication) {

        return ResponseEntity.ok(
                favoriteService.getFavoriteRestaurants(
                        authentication.getName()
                )
        );
    }

    @PostMapping("/api/foods/{foodId}/favorite")
    public ResponseEntity<FavoriteResponse> addFoodFavorite(

            Authentication authentication,

            @PathVariable Long foodId) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        favoriteService.addFoodFavorite(
                                authentication.getName(),
                                foodId
                        )
                );
    }

    @DeleteMapping("/api/foods/{foodId}/favorite")
    public ResponseEntity<String> removeFoodFavorite(

            Authentication authentication,

            @PathVariable Long foodId) {

        favoriteService.removeFoodFavorite(
                authentication.getName(),
                foodId
        );

        return ResponseEntity.ok(
                "Food removed from favorites"
        );
    }

    @GetMapping("/api/foods/favorites")
    public ResponseEntity<List<FavoriteResponse>> getFoodFavorites(

            Authentication authentication) {

        return ResponseEntity.ok(
                favoriteService.getFavoriteFoods(
                        authentication.getName()
                )
        );
    }
}