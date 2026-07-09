package com.jeevan.foodhub.service.impl;

import com.jeevan.foodhub.dto.response.FavoriteResponse;
import com.jeevan.foodhub.entity.*;
import com.jeevan.foodhub.exception.ResourceNotFoundException;
import com.jeevan.foodhub.mapper.FavoriteMapper;
import com.jeevan.foodhub.repository.*;
import com.jeevan.foodhub.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final FavoriteRepository favoriteRepository;

    public FavoriteServiceImpl(
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            FoodRepository foodRepository,
            FavoriteRepository favoriteRepository) {

        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public FavoriteResponse addRestaurantFavorite(
            String userEmail,
            Long restaurantId) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));

        favoriteRepository.findByUserAndRestaurant(user, restaurant)
                .ifPresent(favorite -> {
                    throw new RuntimeException(
                            "Restaurant already in favorites"
                    );
                });

        Favorite favorite = Favorite.builder()
                .favoriteType(FavoriteType.RESTAURANT)
                .user(user)
                .restaurant(restaurant)
                .build();

        return FavoriteMapper.toFavoriteResponse(
                favoriteRepository.save(favorite)
        );
    }
    @Override
    public FavoriteResponse addFoodFavorite(
            String userEmail,
            Long foodId) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found"));

        favoriteRepository.findByUserAndFood(user, food)
                .ifPresent(favorite -> {
                    throw new RuntimeException(
                            "Food already in favorites"
                    );
                });

        Favorite favorite = Favorite.builder()
                .favoriteType(FavoriteType.FOOD)
                .user(user)
                .food(food)
                .build();

        return FavoriteMapper.toFavoriteResponse(
                favoriteRepository.save(favorite)
        );
    }
    @Override
    public List<FavoriteResponse> getFavoriteRestaurants(
            String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return favoriteRepository
                .findByUserAndFavoriteTypeOrderByCreatedAtDesc(
                        user,
                        FavoriteType.RESTAURANT
                )
                .stream()
                .map(FavoriteMapper::toFavoriteResponse)
                .toList();
    }
    @Override
    public List<FavoriteResponse> getFavoriteFoods(
            String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return favoriteRepository
                .findByUserAndFavoriteTypeOrderByCreatedAtDesc(
                        user,
                        FavoriteType.FOOD
                )
                .stream()
                .map(FavoriteMapper::toFavoriteResponse)
                .toList();
    }
    @Override
    public void removeRestaurantFavorite(
            String userEmail,
            Long restaurantId) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));

        Favorite favorite = favoriteRepository
                .findByUserAndRestaurant(user, restaurant)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Favorite not found"
                        ));

        favoriteRepository.delete(favorite);
    }
    @Override
    public void removeFoodFavorite(
            String userEmail,
            Long foodId) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found"));

        Favorite favorite = favoriteRepository
                .findByUserAndFood(user, food)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Favorite not found"
                        ));

        favoriteRepository.delete(favorite);
    }
}