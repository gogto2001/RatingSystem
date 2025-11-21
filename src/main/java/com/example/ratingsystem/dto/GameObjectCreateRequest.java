package com.example.ratingsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GameObjectCreateRequest {
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Text cannot be empty")
    private String text;
}
