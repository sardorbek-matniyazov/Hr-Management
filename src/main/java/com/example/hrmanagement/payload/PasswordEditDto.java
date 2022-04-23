package com.example.hrmanagement.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PasswordEditDto {
    @Size(min = 8, message = "password length should be over than 7 characters")
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "prePassword is required")
    private String prePassword;
}
