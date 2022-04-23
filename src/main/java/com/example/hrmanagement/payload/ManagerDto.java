package com.example.hrmanagement.payload;

import com.example.hrmanagement.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class ManagerDto {

    @Size(min = 4, max = 50, message = "length must be 3 < x < 50")
    @NotBlank(message = "firstname is required")
    private String firstname;

    @Size(min = 4, max = 50, message = "length must be 3 < x < 50")
    @NotBlank(message = "lastname is required")
    private String lastname;

    @Size(min = 4, max = 50, message = "length must be 3 < x < 50")
    @NotBlank(message = "username is required")
    private String username;

    @NotNull(message = "company is required")
    private Long companyId;

    @Size(min = 8, max = 50, message = "password's length must be 7 < x < 50")
    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "manager role is required")
    private String role;

    @Email(message = "email not valid")
    @NotBlank(message = "email is required")
    private String email;

    public User toUser() {
        return new User(this.firstname, this.lastname, this.username, this.password, this.email);
    }
}
