package com.jeevan.foodhub.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeevan.foodhub.entity.Food;
import com.jeevan.foodhub.entity.FoodCategory;
import com.jeevan.foodhub.entity.Restaurant;
import com.jeevan.foodhub.repository.FoodRepository;
import com.jeevan.foodhub.repository.RestaurantRepository;
import com.jeevan.foodhub.seed.FoodSeed;
import com.jeevan.foodhub.seed.RestaurantSeed;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final ObjectMapper objectMapper;

    public DatabaseSeeder(
            RestaurantRepository restaurantRepository,
            FoodRepository foodRepository,
            ObjectMapper objectMapper) {

        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (restaurantRepository.count() != 0) {
            return;
        }
        loadRestaurants();
        System.out.println("Restaurants Loaded");
        loadFoods();
    }

    private void loadRestaurants() throws Exception {

        InputStream inputStream =
                getClass().getResourceAsStream("/seed/restaurants.json");
        RestaurantSeed[] restaurants =
                objectMapper.readValue(
                        inputStream,
                        RestaurantSeed[].class
                );

        for (RestaurantSeed r : restaurants) {

            Restaurant restaurant = Restaurant.builder()
                    .name(r.getName())
                    .description(r.getDescription())
                    .address(r.getAddress())
                    .phone(r.getPhone())
                    .imageUrl(r.getImageUrl())
                    .isOpen(r.getIsOpen())
                    .build();

            restaurantRepository.save(restaurant);
        }
    }

    private void loadFoods() throws Exception {

        InputStream inputStream =
                getClass().getResourceAsStream("/seed/foods.json");
        FoodSeed[] foods =
                objectMapper.readValue(
                        inputStream,
                        FoodSeed[].class
                );

        for (FoodSeed f : foods) {

            Restaurant restaurant =
                    restaurantRepository
                            .findByName(f.getRestaurant())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Restaurant not found : "
                                                    + f.getRestaurant()));

            Food food = Food.builder()
                    .name(f.getName())
                    .description(f.getDescription())
                    .price(f.getPrice())
                    .imageUrl(f.getImageUrl())
                    .available(true)
                    .category(FoodCategory.valueOf(f.getCategory()))
                    .restaurant(restaurant)
                    .build();

            foodRepository.save(food);
        }
    }
}