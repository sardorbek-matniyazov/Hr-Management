package com.example.hrmanagement.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank(message = "username is required for login")
    private String username;
    @NotBlank(message = "password shouldn't be empty")
    private String password;
}
