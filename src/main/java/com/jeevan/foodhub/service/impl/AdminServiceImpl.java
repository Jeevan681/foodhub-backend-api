package com.jeevan.foodhub.service.impl;

import com.jeevan.foodhub.dto.response.AdminDashboardResponse;
import com.jeevan.foodhub.dto.response.RestaurantSummaryResponse;
import com.jeevan.foodhub.dto.response.UserSummaryResponse;
import com.jeevan.foodhub.entity.OrderStatus;
import com.jeevan.foodhub.entity.Restaurant;
import com.jeevan.foodhub.entity.User;
import com.jeevan.foodhub.exception.ResourceNotFoundException;
import com.jeevan.foodhub.mapper.RestaurantMapper;
import com.jeevan.foodhub.mapper.UserMapper;
import com.jeevan.foodhub.repository.*;
import com.jeevan.foodhub.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.jeevan.foodhub.mapper.RestaurantMapper;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;

    public AdminServiceImpl(
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            FoodRepository foodRepository,
            OrderRepository orderRepository,
            ReviewRepository reviewRepository,
            FavoriteRepository favoriteRepository) {

        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public AdminDashboardResponse getDashboard() {

        return AdminDashboardResponse.builder()
                .totalUsers(userRepository.count())
                .totalRestaurants(restaurantRepository.count())
                .totalFoods(foodRepository.count())
                .totalOrders(orderRepository.count())

                .pendingOrders(orderRepository.countByStatus(OrderStatus.PENDING))
                .completedOrders(orderRepository.countByStatus(OrderStatus.DELIVERED))
                .cancelledOrders(orderRepository.countByStatus(OrderStatus.CANCELLED))

                .totalRevenue(orderRepository.getTotalRevenue())

                .totalReviews(reviewRepository.count())
                .totalFavorites(favoriteRepository.count())

                .build();
    }
    @Override
    public List<UserSummaryResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toSummary)
                .toList();
    }

    @Override
    public UserSummaryResponse getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        return UserMapper.toSummary(user);
    }

    @Override
    public List<UserSummaryResponse> searchUsers(String keyword) {

        return userRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(UserMapper::toSummary)
                .toList();
    }

    @Override
    public void blockUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        user.setBlocked(true);

        userRepository.save(user);
    }

    @Override
    public void unblockUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        user.setBlocked(false);

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

    @Override
    public List<RestaurantSummaryResponse> getAllRestaurants() {

        return restaurantRepository.findAll()
                .stream()
                .map(RestaurantMapper::toSummary)
                .toList();
    }

    @Override
    public RestaurantSummaryResponse getRestaurant(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurant not found with id: " + id));

        return RestaurantMapper.toSummary(restaurant);
    }

    @Override
    public List<RestaurantSummaryResponse> searchRestaurants(String keyword) {

        return restaurantRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(RestaurantMapper::toSummary)
                .toList();
    }

    @Override
    public void openRestaurant(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurant not found with id: " + id));

        restaurant.setIsOpen(true);

        restaurantRepository.save(restaurant);
    }

    @Override
    public void closeRestaurant(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurant not found with id: " + id));

        restaurant.setIsOpen(false);

        restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurant not found with id: " + id));

        restaurantRepository.delete(restaurant);
    }

}