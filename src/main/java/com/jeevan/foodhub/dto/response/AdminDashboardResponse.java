package com.jeevan.foodhub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {

    private Long totalUsers;
    private Long totalRestaurants;
    private Long totalFoods;
    private Long totalOrders;

    private Double totalRevenue;

    private Long pendingOrders;
    private Long completedOrders;
    private Long cancelledOrders;

    private Long totalReviews;
    private Long totalFavorites;
}