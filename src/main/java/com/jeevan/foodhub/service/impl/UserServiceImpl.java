package com.jeevan.foodhub.service.impl;

import com.jeevan.foodhub.dto.request.RegisterRequest;
import com.jeevan.foodhub.dto.response.RegisterResponse;
import com.jeevan.foodhub.entity.Role;
import com.jeevan.foodhub.entity.User;
import com.jeevan.foodhub.exception.EmailAlreadyExistsException;
import com.jeevan.foodhub.repository.UserRepository;
import com.jeevan.foodhub.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .blocked(false)
                .build();

        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .message("User registered successfully")
                .build();
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
