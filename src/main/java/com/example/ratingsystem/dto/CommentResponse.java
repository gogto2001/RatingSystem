package com.example.ratingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String message;
    private int rating;
    private boolean approved;
    private Long authorId;
    private LocalDateTime createdAt;
}
