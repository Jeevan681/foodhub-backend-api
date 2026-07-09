package com.jeevan.foodhub.service;

import com.jeevan.foodhub.dto.request.RegisterRequest;
import com.jeevan.foodhub.dto.response.RegisterResponse;
import com.jeevan.foodhub.entity.User;

import java.util.List;

public interface UserService {
    RegisterResponse register(RegisterRequest request);
    List<User> getAllUsers();
}
