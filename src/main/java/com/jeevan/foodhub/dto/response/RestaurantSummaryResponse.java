package com.jeevan.foodhub.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantSummaryResponse {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private Boolean isOpen;
    private Integer totalFoods;
}