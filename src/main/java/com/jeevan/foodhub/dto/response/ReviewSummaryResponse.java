package com.jeevan.foodhub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewSummaryResponse {

    private Double averageRating;

    private Long reviewCount;
}