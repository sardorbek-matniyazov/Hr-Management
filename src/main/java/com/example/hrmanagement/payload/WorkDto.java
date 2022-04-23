package com.example.hrmanagement.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class WorkDto {
    @NotBlank(message = "work name is required")
    private String name;
    @NotBlank(message = "work description is required")
    private String description;
    @NotNull(message = "work expire day is required")
    private Long expireDay;
    @NotNull(message = "worker is required")
    private UUID workerId;
}
