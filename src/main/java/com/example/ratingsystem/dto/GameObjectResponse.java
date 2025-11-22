package com.example.ratingsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GameObjectResponse {
    private Long id;
    private String title;
    private String text;
    private Long sellerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
