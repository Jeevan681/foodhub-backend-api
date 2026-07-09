package com.jeevan.foodhub.service.impl;

import com.jeevan.foodhub.dto.request.CreateFoodRequest;
import com.jeevan.foodhub.dto.request.UpdateFoodRequest;
import com.jeevan.foodhub.dto.response.FoodResponse;
import com.jeevan.foodhub.entity.Food;
import com.jeevan.foodhub.entity.FoodCategory;
import com.jeevan.foodhub.entity.Restaurant;
import com.jeevan.foodhub.exception.ResourceNotFoundException;
import com.jeevan.foodhub.mapper.FoodMapper;
import com.jeevan.foodhub.repository.FoodRepository;
import com.jeevan.foodhub.repository.RestaurantRepository;
import com.jeevan.foodhub.service.FoodService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    public FoodServiceImpl(
            FoodRepository foodRepository,
            RestaurantRepository restaurantRepository) {

        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public FoodResponse createFood(
            Long restaurantId,
            CreateFoodRequest request) {

        Restaurant restaurant =
                restaurantRepository.findById(restaurantId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Restaurant not found"));

        Food food = Food.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .available(request.getAvailable())
                .category(request.getCategory())
                .restaurant(restaurant)
                .build();

        Food savedFood = foodRepository.save(food);

        return FoodMapper.toResponse(savedFood);
    }
    @Override
    public List<FoodResponse> getFoodsByRestaurant(Long restaurantId) {

        restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));

        return foodRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(FoodMapper::toResponse)
                .toList();
    }

    @Override
    public FoodResponse getFoodById(Long id) {

        Food food = foodRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found"));

        return FoodMapper.toResponse(food);
    }
    @Override
    public FoodResponse updateFood(
            Long id,
            UpdateFoodRequest request) {

        Food food = foodRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found"));

        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setImageUrl(request.getImageUrl());
        food.setAvailable(request.getAvailable());
        food.setCategory(request.getCategory());

        Food updatedFood = foodRepository.save(food);

        return FoodMapper.toResponse(updatedFood);
    }
    @Override
    public void deleteFood(Long id) {

        Food food = foodRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found"));

        foodRepository.delete(food);
    }
    @Override
    public List<FoodResponse> searchFoods(String name) {

        return foodRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(FoodMapper::toResponse)
                .toList();
    }
    @Override
    public List<FoodResponse> getFoodsByCategory(FoodCategory category) {

        return foodRepository.findByCategory(category)
                .stream()
                .map(FoodMapper::toResponse)
                .toList();
    }
    @Override
    public Page<FoodResponse> getFoods(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageable =
                PageRequest.of(page, size, sort);

        Page<Food> foods =
                foodRepository.findAll(pageable);

        return foods.map(FoodMapper::toResponse);
    }

}