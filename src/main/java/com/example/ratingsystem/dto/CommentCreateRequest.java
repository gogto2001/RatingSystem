package com.example.ratingsystem.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class CommentCreateRequest {
    @NotBlank(message = "Message cannot be empty")
    private String message;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

}
