package com.jeevan.foodhub.service;

import com.jeevan.foodhub.dto.request.LoginRequest;
import com.jeevan.foodhub.dto.response.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

}