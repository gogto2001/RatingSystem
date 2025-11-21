package com.example.ratingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SellerStatisticsResponse {
    private Long sellerId;
    private String sellerName;

    private double averageRating;
    private int ratingCount;
    private int ratingSum;

    private int approvedCommentsCount;
    private int totalCommentsCount;

    private int gameObjectCount;
}
