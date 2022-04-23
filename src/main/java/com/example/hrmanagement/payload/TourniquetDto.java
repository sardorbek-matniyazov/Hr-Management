package com.example.hrmanagement.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class TourniquetDto {
    private UUID userId;
    private boolean openIn;
}
