package com.jeevan.foodhub.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invaild email")
    @NotBlank(message = "email is reuired")
    private String email;

    @Size(min= 6,message = "Password must contains atleast 6 characters")
    private String password;
}
