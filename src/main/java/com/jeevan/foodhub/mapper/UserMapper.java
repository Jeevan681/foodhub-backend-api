package com.jeevan.foodhub.mapper;

import com.jeevan.foodhub.dto.response.UserSummaryResponse;
import com.jeevan.foodhub.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserSummaryResponse toSummary(User user) {
        return UserSummaryResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .blocked(user.getBlocked())
                .build();
    }
}