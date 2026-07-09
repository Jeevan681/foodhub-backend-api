package com.jeevan.foodhub.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.PriorityQueue;

@Data
@Builder
public class RegisterResponse {
    private Long id;

    private String name;
    private String email;
    private String message;
}
