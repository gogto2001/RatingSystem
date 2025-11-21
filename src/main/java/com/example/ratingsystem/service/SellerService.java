package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.SellerStatisticsResponse;

public interface SellerService {
    SellerStatisticsResponse getSellerStatistics(Long sellerId);

}
