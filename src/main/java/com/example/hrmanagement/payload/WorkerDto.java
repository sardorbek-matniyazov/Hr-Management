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
public class WorkerDto {

    @Size(min = 4, max = 50, message = "length must be 3 < x < 50")
    @NotBlank(message = "firstname is required")
    private String firstname;

    @Size(min = 4, max = 50, message = "length must be 3 < x < 50")
    @NotBlank(message = "lastname is required")
    private String lastname;

    private String username;

    @NotNull(message = "company is required")
    private Long companyId;

    @Email(message = "email not valid")
    @NotBlank(message = "email is required")
    private String email;

    public User toUser() {
        return new User(this.firstname, this.lastname, this.username, this.email);
    }

}
