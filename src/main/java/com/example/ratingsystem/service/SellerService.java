package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.SellerStatisticsResponse;

import java.util.List;

public interface SellerService {

    // ერთი სელერის სტატისტიკა
    SellerStatisticsResponse getSellerStatistics(Long sellerId);

    // ✅ TOP Sellers-ების სია
    List<SellerStatisticsResponse> getTopSellers(int limit);
}
