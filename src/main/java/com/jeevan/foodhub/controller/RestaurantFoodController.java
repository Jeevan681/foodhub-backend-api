package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.request.CreateFoodRequest;
import com.jeevan.foodhub.dto.response.FoodResponse;
import com.jeevan.foodhub.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantFoodController {

    private final FoodService foodService;

    public RestaurantFoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/{restaurantId}/foods")
    public ResponseEntity<FoodResponse> createFood(

            @PathVariable Long restaurantId,

            @Valid
            @RequestBody CreateFoodRequest request) {

        FoodResponse response =
                foodService.createFood(
                        restaurantId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{restaurantId}/foods")
    public ResponseEntity<List<FoodResponse>> getFoodsByRestaurant(

            @PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                foodService.getFoodsByRestaurant(
                        restaurantId
                )
        );
    }
}