package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.request.PlaceOrderRequest;
import com.jeevan.foodhub.dto.request.UpdateOrderStatusRequest;
import com.jeevan.foodhub.dto.response.OrderResponse;
import com.jeevan.foodhub.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(
            OrderService orderService) {

        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(

            Authentication authentication,

            @Valid
            @RequestBody
            PlaceOrderRequest request) {

        return ResponseEntity.ok(

                orderService.placeOrder(
                        authentication.getName(),
                        request
                )
        );
    }
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(

            Authentication authentication) {

        return ResponseEntity.ok(

                orderService.getMyOrders(
                        authentication.getName()
                )
        );
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(

            Authentication authentication,

            @PathVariable Long orderId) {

        return ResponseEntity.ok(

                orderService.getOrderById(
                        authentication.getName(),
                        orderId
                )
        );
    }
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(

            @PathVariable Long orderId,

            @Valid
            @RequestBody
            UpdateOrderStatusRequest request) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(
                        orderId,
                        request
                )
        );
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(

            Authentication authentication,

            @PathVariable Long orderId) {

        orderService.cancelOrder(
                authentication.getName(),
                orderId
        );

        return ResponseEntity.ok(
                "Order cancelled successfully"
        );
    }
}