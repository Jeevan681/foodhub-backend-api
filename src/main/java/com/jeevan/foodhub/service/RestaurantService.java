package com.jeevan.foodhub.service;

import com.jeevan.foodhub.dto.request.CreateRestaurantRequest;
import com.jeevan.foodhub.dto.request.UpdateRestaurantRequest;
import com.jeevan.foodhub.dto.response.RestaurantResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RestaurantService {

    RestaurantResponse createRestaurant(CreateRestaurantRequest request);
    RestaurantResponse getRestaurantById(Long id);
    List<RestaurantResponse> getAllRestaurants();
    RestaurantResponse updateRestaurant(Long id, UpdateRestaurantRequest request);
    void deleteRestaurant(Long id);
    List<RestaurantResponse> searchRestaurants(String name);
    Page<RestaurantResponse> getRestaurants(
            int page,
            int size,
            String sortBy,
            String direction
    );
}