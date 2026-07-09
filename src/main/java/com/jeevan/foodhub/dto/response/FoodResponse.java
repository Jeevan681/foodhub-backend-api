package com.jeevan.foodhub.dto.response;

import com.jeevan.foodhub.entity.FoodCategory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodResponse {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private String imageUrl;

    private Boolean available;

    private FoodCategory category;

    private Long restaurantId;

    private String restaurantName;
}