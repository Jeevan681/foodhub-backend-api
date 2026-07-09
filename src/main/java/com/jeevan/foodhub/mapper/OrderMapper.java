package com.jeevan.foodhub.mapper;

import com.jeevan.foodhub.dto.response.OrderItemResponse;
import com.jeevan.foodhub.dto.response.OrderResponse;
import com.jeevan.foodhub.entity.Order;
import com.jeevan.foodhub.entity.OrderItem;

import java.util.List;

public class OrderMapper {

    private OrderMapper() {
    }

    public static OrderItemResponse toOrderItemResponse(OrderItem item) {

        double subtotal = item.getPrice() * item.getQuantity();

        return OrderItemResponse.builder()
                .id(item.getId())
                .foodId(item.getFood().getId())
                .foodName(item.getFood().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .subtotal(subtotal)
                .build();
    }

    public static OrderResponse toOrderResponse(Order order) {

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(OrderMapper::toOrderItemResponse)
                .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .deliveryAddress(order.getDeliveryAddress())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}