package com.jeevan.foodhub.seed;

import lombok.Data;

@Data
public class FoodSeed {

    private String restaurant;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
}