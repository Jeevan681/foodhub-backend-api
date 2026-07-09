package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.request.LoginRequest;
import com.jeevan.foodhub.dto.response.LoginResponse;
import com.jeevan.foodhub.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }
}