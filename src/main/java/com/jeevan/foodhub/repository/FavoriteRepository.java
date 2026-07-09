package com.jeevan.foodhub.repository;

import com.jeevan.foodhub.entity.Favorite;
import com.jeevan.foodhub.entity.Food;
import com.jeevan.foodhub.entity.Restaurant;
import com.jeevan.foodhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUserOrderByCreatedAtDesc(User user);

    List<Favorite> findByUserAndFavoriteTypeOrderByCreatedAtDesc(
            User user,
            com.jeevan.foodhub.entity.FavoriteType favoriteType
    );

    Optional<Favorite> findByUserAndRestaurant(
            User user,
            Restaurant restaurant
    );

    Optional<Favorite> findByUserAndFood(
            User user,
            Food food
    );

    long countByRestaurant(Restaurant restaurant);

    long countByFood(Food food);
}