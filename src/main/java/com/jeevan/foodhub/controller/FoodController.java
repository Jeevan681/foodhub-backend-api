package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.request.CreateFoodRequest;
import com.jeevan.foodhub.dto.request.UpdateFoodRequest;
import com.jeevan.foodhub.dto.response.FoodResponse;
import com.jeevan.foodhub.entity.FoodCategory;
import com.jeevan.foodhub.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }


    @GetMapping("/api/foods/{id}")
    public ResponseEntity<FoodResponse> getFoodById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                foodService.getFoodById(id)
        );
    }
    @PutMapping("/api/foods/{id}")
    public ResponseEntity<FoodResponse> updateFood(
            @PathVariable Long id,
            @Valid @RequestBody UpdateFoodRequest request) {

        return ResponseEntity.ok(
                foodService.updateFood(id, request)
        );
    }
    @DeleteMapping("/api/foods/{id}")
    public ResponseEntity<String> deleteFood(
            @PathVariable Long id) {

        foodService.deleteFood(id);

        return ResponseEntity.ok("Food deleted successfully");
    }
    @GetMapping("/api/foods/search")
    public ResponseEntity<List<FoodResponse>> searchFoods(
            @RequestParam String name) {

        return ResponseEntity.ok(
                foodService.searchFoods(name)
        );
    }
    @GetMapping("/api/foods/category/{category}")
    public ResponseEntity<List<FoodResponse>> getFoodsByCategory(@PathVariable FoodCategory category) {

        return ResponseEntity.ok(
                foodService.getFoodsByCategory(category)
        );
    }
    @GetMapping("/api/foods")
    public ResponseEntity<Page<FoodResponse>> getFoods(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        return ResponseEntity.ok(foodService.getFoods(page,size,sortBy,direction));
    }
    @PostMapping("/api/admin/restaurants/{restaurantId}/foods")
    public ResponseEntity<FoodResponse> createFood(
            @PathVariable Long restaurantId,
            @Valid @RequestBody CreateFoodRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(foodService.createFood(restaurantId, request));
    }
}