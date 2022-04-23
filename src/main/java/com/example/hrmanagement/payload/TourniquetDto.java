package com.example.hrmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TourniquetDto {
    @NotNull(message = "user is required")
    private UUID userId;
    @NotNull(message = "tourniquet type is required")
    private boolean openIn;
}
