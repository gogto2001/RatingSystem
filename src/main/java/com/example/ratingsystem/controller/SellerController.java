package com.example.ratingsystem.controller;

import com.example.ratingsystem.dto.SellerStatisticsResponse;
import com.example.ratingsystem.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {
    private final SellerService sellerService;

    @GetMapping("/{id}/statistics")
    public SellerStatisticsResponse getStats(@PathVariable Long id) {
        return sellerService.getSellerStatistics(id);
    }
}
