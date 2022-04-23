package com.example.hrmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SalaryDto {
    @NotNull(message = "user is required")
    private UUID userId;
    @NotNull(message = "salary is required")
    private Double salary;
    @NotNull(message = "date is required")
    private Date date;
}