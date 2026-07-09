package com.jeevan.foodhub.controller;


import com.jeevan.foodhub.dto.request.RegisterRequest;
import com.jeevan.foodhub.dto.response.RegisterResponse;
import com.jeevan.foodhub.entity.User;
import com.jeevan.foodhub.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }
}
