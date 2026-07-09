package com.jeevan.foodhub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrderRequest {

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;
}