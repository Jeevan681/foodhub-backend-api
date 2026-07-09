package com.jeevan.foodhub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {

    private Long id;

    private Long foodId;

    private String foodName;

    private Double price;

    private Integer quantity;

    private Double subtotal;
}