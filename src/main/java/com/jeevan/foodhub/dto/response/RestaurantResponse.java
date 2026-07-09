package com.jeevan.foodhub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {

    private Long id;

    private String name;

    private String description;

    private String address;

    private String phone;

    private String imageUrl;

    private Boolean isOpen;
}