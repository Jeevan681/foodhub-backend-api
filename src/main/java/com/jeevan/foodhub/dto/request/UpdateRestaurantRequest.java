package com.jeevan.foodhub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRestaurantRequest {

    @NotBlank(message = "Restaurant name is required")
    private String name;

    private String description;

    private String address;

    private String phone;

    private String imageUrl;
}