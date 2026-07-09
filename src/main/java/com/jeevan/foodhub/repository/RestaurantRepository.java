package com.jeevan.foodhub.repository;

import com.jeevan.foodhub.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    List<Restaurant> findByIsOpen(Boolean isOpen);
    Optional<Restaurant> findByName(String name);
    Page<Restaurant> findAll(Pageable pageable);

}