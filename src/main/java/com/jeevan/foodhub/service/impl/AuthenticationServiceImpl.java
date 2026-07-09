package com.jeevan.foodhub.service.impl;

import com.jeevan.foodhub.dto.request.LoginRequest;
import com.jeevan.foodhub.dto.response.LoginResponse;
import com.jeevan.foodhub.security.JwtService;
import com.jeevan.foodhub.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(
                (org.springframework.security.core.userdetails.UserDetails)
                        authentication.getPrincipal()
        );

        return LoginResponse.builder()
                .token(token)
                .message("Login Successful")
                .build();
    }
}