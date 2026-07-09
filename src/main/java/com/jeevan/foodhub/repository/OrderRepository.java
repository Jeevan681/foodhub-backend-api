package com.jeevan.foodhub.repository;

import com.jeevan.foodhub.entity.Order;
import com.jeevan.foodhub.entity.OrderStatus;
import com.jeevan.foodhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByStatus(OrderStatus status);
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    long countByStatus(OrderStatus status);
    @Query("""
            SELECT COALESCE(SUM(o.totalAmount), 0)
            FROM Order o
            WHERE o.status = com.jeevan.foodhub.entity.OrderStatus.DELIVERED
            """)
    Double getTotalRevenue();
    List<Order> findAllByOrderByCreatedAtDesc();
    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);
}