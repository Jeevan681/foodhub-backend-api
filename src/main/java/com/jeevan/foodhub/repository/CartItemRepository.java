package com.jeevan.foodhub.repository;

import com.jeevan.foodhub.entity.Cart;
import com.jeevan.foodhub.entity.CartItem;
import com.jeevan.foodhub.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByCartAndFood(Cart cart, Food food);
    void deleteByCart(Cart cart);
}