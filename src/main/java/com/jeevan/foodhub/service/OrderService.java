package com.jeevan.foodhub.service;

import com.jeevan.foodhub.dto.request.PlaceOrderRequest;
import com.jeevan.foodhub.dto.request.UpdateOrderStatusRequest;
import com.jeevan.foodhub.dto.response.OrderResponse;
import com.jeevan.foodhub.entity.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(
            String userEmail,
            PlaceOrderRequest request
    );
    List<OrderResponse> getMyOrders(String userEmail);
    OrderResponse getOrderById(String userEmail, Long orderId);
    OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request);
    void cancelOrder(String userEmail, Long orderId);
    List<OrderResponse> getAllOrders();

    List<OrderResponse> getOrdersByStatus(OrderStatus status);
}