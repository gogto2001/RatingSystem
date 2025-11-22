package com.example.ratingsystem.controller;

import com.example.ratingsystem.dto.SellerStatisticsResponse;
import com.example.ratingsystem.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    // არსებული endpoint — ერთი სელერის სტატისტიკა
    @GetMapping("/{id}/statistics")
    public SellerStatisticsResponse getStats(@PathVariable Long id) {
        return sellerService.getSellerStatistics(id);
    }

    // ✅ ახალი endpoint — TOP sellers
    // GET /seller/top?limit=10
    @GetMapping("/top")
    public List<SellerStatisticsResponse> getTopSellers(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return sellerService.getTopSellers(limit);
    }
}
