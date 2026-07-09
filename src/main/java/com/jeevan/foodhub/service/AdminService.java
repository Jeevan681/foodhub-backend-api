package com.jeevan.foodhub.service;
import com.jeevan.foodhub.dto.response.AdminDashboardResponse;
import com.jeevan.foodhub.dto.response.RestaurantSummaryResponse;
import com.jeevan.foodhub.dto.response.UserSummaryResponse;

import java.util.List;

public interface AdminService {

    AdminDashboardResponse getDashboard();
    List<UserSummaryResponse> getAllUsers();

    UserSummaryResponse getUser(Long id);

    List<UserSummaryResponse> searchUsers(String keyword);

    void blockUser(Long id);

    void unblockUser(Long id);

    void deleteUser(Long id);

    List<RestaurantSummaryResponse> getAllRestaurants();

    RestaurantSummaryResponse getRestaurant(Long id);

    List<RestaurantSummaryResponse> searchRestaurants(String keyword);

    void openRestaurant(Long id);

    void closeRestaurant(Long id);

    void deleteRestaurant(Long id);

}