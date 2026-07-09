package com.jeevan.foodhub.seed;

import lombok.Data;

@Data
public class RestaurantSeed {

    private String name;
    private String description;
    private String address;
    private String phone;
    private String imageUrl;
    private Boolean isOpen;
}