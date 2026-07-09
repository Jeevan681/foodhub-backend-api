package com.jeevan.foodhub.dto.request;

import com.jeevan.foodhub.entity.FoodCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFoodRequest {

    @NotBlank(message = "Food name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    private String imageUrl;

    private Boolean available;

    @NotNull(message = "Category is required")
    private FoodCategory category;
}