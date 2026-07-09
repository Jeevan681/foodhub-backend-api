package com.jeevan.foodhub.repository;

import com.jeevan.foodhub.entity.Order;
import com.jeevan.foodhub.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);

}