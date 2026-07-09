package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.request.UpdateOrderStatusRequest;
import com.jeevan.foodhub.dto.response.OrderResponse;
import com.jeevan.foodhub.entity.OrderStatus;
import com.jeevan.foodhub.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(
            @PathVariable OrderStatus status) {

        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(id, request));
    }
}