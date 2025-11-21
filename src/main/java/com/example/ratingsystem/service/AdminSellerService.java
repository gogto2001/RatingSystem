package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.UserResponse;

public interface AdminSellerService {
    UserResponse approveSeller(Long sellerId);
    UserResponse rejectSeller(Long sellerId);
}
