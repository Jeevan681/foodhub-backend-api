package com.jeevan.foodhub.dto.response;

import com.jeevan.foodhub.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long orderId;

    private Long userId;

    private String deliveryAddress;

    private Double totalAmount;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;
}