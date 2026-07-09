package com.jeevan.foodhub.dto.response;

import com.jeevan.foodhub.entity.ReviewType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private Long id;

    private Long userId;

    private String userName;

    private Integer rating;

    private String comment;

    private ReviewType reviewType;

    private Long restaurantId;

    private String restaurantName;

    private Long foodId;

    private String foodName;

    private LocalDateTime createdAt;
}