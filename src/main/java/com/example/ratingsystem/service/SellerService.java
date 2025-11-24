package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.SellerStatisticsResponse;

import java.util.List;

public interface SellerService {


    SellerStatisticsResponse getSellerStatistics(Long sellerId);


    List<SellerStatisticsResponse> getTopSellers(int limit);
}
