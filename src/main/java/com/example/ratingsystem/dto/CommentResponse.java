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
    private Integer rating;
    private Long authorId;
    private Long sellerId;
    private boolean approved;
    private LocalDateTime createdAt;
}
