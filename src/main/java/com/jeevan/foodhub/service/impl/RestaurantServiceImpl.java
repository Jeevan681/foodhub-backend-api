package com.jeevan.foodhub.service.impl;

import com.jeevan.foodhub.dto.request.CreateRestaurantRequest;
import com.jeevan.foodhub.dto.request.UpdateRestaurantRequest;
import com.jeevan.foodhub.dto.response.RestaurantResponse;
import com.jeevan.foodhub.entity.Restaurant;
import com.jeevan.foodhub.exception.ResourceNotFoundException;
import com.jeevan.foodhub.mapper.RestaurantMapper;
import com.jeevan.foodhub.repository.RestaurantRepository;
import com.jeevan.foodhub.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    @Override
    public RestaurantResponse getRestaurantById(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));
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
    @Override
    public RestaurantResponse createRestaurant(CreateRestaurantRequest request) {

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .phone(request.getPhone())
                .imageUrl(request.getImageUrl())
                .build();

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantResponse.builder()
                .id(savedRestaurant.getId())
                .name(savedRestaurant.getName())
                .description(savedRestaurant.getDescription())
                .address(savedRestaurant.getAddress())
                .phone(savedRestaurant.getPhone())
                .imageUrl(savedRestaurant.getImageUrl())
                .isOpen(savedRestaurant.getIsOpen())
                .build();
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {

        return restaurantRepository.findAll()
                .stream()
                .map(restaurant -> RestaurantResponse.builder()
                        .id(restaurant.getId())
                        .name(restaurant.getName())
                        .description(restaurant.getDescription())
                        .address(restaurant.getAddress())
                        .phone(restaurant.getPhone())
                        .imageUrl(restaurant.getImageUrl())
                        .isOpen(restaurant.getIsOpen())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantResponse updateRestaurant(Long id,
                                               UpdateRestaurantRequest request) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));

        restaurant.setName(request.getName());
        restaurant.setDescription(request.getDescription());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhone(request.getPhone());
        restaurant.setImageUrl(request.getImageUrl());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantMapper.toResponse(updatedRestaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<RestaurantResponse> searchRestaurants(String name) {

        return restaurantRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(RestaurantMapper::toResponse)
                .toList();
    }
    @Override
    public Page<RestaurantResponse> getRestaurants(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageRequest =
                PageRequest.of(page, size, sort);

        Page<Restaurant> restaurants =
                restaurantRepository.findAll(pageRequest);

        return restaurants.map(RestaurantMapper::toResponse);
    }

}