package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.request.CreateRestaurantRequest;
import com.jeevan.foodhub.dto.request.UpdateRestaurantRequest;
import com.jeevan.foodhub.dto.response.RestaurantResponse;
import com.jeevan.foodhub.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @Valid @RequestBody CreateRestaurantRequest request) {

        RestaurantResponse response =
                restaurantService.createRestaurant(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {

        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                restaurantService.getRestaurantById(id)
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRestaurantRequest request) {

        RestaurantResponse response =
                restaurantService.updateRestaurant(id, request);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(
            @PathVariable Long id) {

        restaurantService.deleteRestaurant(id);

        return ResponseEntity.ok("Restaurant deleted successfully");
    }
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResponse>> searchRestaurants(
            @RequestParam String name) {

        return ResponseEntity.ok(
                restaurantService.searchRestaurants(name)
        );
    }
    @GetMapping("/page")
    public ResponseEntity<Page<RestaurantResponse>> getRestaurants(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                restaurantService.getRestaurants(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }
}