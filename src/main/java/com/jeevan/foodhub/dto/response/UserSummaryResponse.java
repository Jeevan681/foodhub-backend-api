package com.jeevan.foodhub.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryResponse {

    private Long id;
    private String name;
    private String email;
    private String role;
    private Boolean blocked;
}