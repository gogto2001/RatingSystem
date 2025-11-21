package com.example.ratingsystem.controller;

import com.example.ratingsystem.dto.UserResponse;
import com.example.ratingsystem.service.AdminSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/sellers")
public class AdminSellerController {

    private final AdminSellerService adminSellerService;

    @PutMapping("/{id}/approve")
    public UserResponse approveSeller(@PathVariable Long id) {
        return adminSellerService.approveSeller(id);
    }

    @PutMapping("/{id}/reject")
    public UserResponse rejectSeller(@PathVariable Long id) {
        return adminSellerService.rejectSeller(id);
    }
}
