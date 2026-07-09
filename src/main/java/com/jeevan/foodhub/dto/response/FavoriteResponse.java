package com.jeevan.foodhub.dto.response;

import com.jeevan.foodhub.entity.FavoriteType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResponse {

    private Long id;

    private Long userId;

    private FavoriteType favoriteType;

    private Long restaurantId;

    private String restaurantName;

    private Long foodId;

    private String foodName;

    private LocalDateTime createdAt;
}