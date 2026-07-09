package com.jeevan.foodhub.service.impl;

import com.jeevan.foodhub.dto.request.PlaceOrderRequest;
import com.jeevan.foodhub.dto.request.UpdateOrderStatusRequest;
import com.jeevan.foodhub.dto.response.OrderResponse;
import com.jeevan.foodhub.entity.*;
import com.jeevan.foodhub.exception.ResourceNotFoundException;
import com.jeevan.foodhub.mapper.OrderMapper;
import com.jeevan.foodhub.repository.*;
import com.jeevan.foodhub.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(
            UserRepository userRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository) {

        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(
            String userEmail,
            PlaceOrderRequest request) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = Order.builder()
                .user(user)
                .deliveryAddress(request.getDeliveryAddress())
                .status(OrderStatus.PENDING)
                .build();

        order = orderRepository.save(order);

        double total = 0;

        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .food(cartItem.getFood())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getFood().getPrice())
                    .build();

            total += orderItem.getPrice() * orderItem.getQuantity();

            orderItemRepository.save(orderItem);

            order.getItems().add(orderItem);
        }

        order.setTotalAmount(total);

        orderRepository.save(order);

        cartItemRepository.deleteByCart(cart);

        return OrderMapper.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getMyOrders(String userEmail) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        List<Order> orders =
                orderRepository.findByUserOrderByCreatedAtDesc(user);

        for (Order order : orders) {

            order.setItems(
                    orderItemRepository.findByOrder(order)
            );
        }

        return orders.stream()
                .map(OrderMapper::toOrderResponse)
                .toList();
    }
    @Override
    public OrderResponse getOrderById(
            String userEmail,
            Long orderId) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "Order does not belong to this user"
            );
        }

        order.setItems(
                orderItemRepository.findByOrder(order)
        );

        return OrderMapper.toOrderResponse(order);
    }
    @Override
    public OrderResponse updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequest request) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        order.setStatus(request.getStatus());

        orderRepository.save(order);

        order.setItems(
                orderItemRepository.findByOrder(order)
        );

        return OrderMapper.toOrderResponse(order);
    }
    @Override
    public void cancelOrder(
            String userEmail,
            Long orderId) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "Order does not belong to this user"
            );
        }

        if (order.getStatus() != OrderStatus.PENDING &&
                order.getStatus() != OrderStatus.CONFIRMED) {

            throw new RuntimeException(
                    "Order cannot be cancelled"
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }
    @Override
    public List<OrderResponse> getAllOrders() {

        return orderRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .peek(order ->
                        order.setItems(
                                orderItemRepository.findByOrder(order)))
                .map(OrderMapper::toOrderResponse)
                .toList();
    }
    @Override
    public List<OrderResponse> getOrdersByStatus(OrderStatus status) {

        return orderRepository
                .findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .peek(order ->
                        order.setItems(
                                orderItemRepository.findByOrder(order)))
                .map(OrderMapper::toOrderResponse)
                .toList();
    }
}