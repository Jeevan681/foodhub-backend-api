package com.jeevan.foodhub.repository;

import com.jeevan.foodhub.entity.Cart;
import com.jeevan.foodhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}