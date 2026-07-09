package com.jeevan.foodhub.dto.request;

import com.jeevan.foodhub.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrderStatusRequest {

    @NotNull(message = "Order status is required")
    private OrderStatus status;
}