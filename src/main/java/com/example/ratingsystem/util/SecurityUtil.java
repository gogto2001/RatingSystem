package com.example.ratingsystem.util;


import com.example.ratingsystem.exception.BadRequestException;
import com.example.ratingsystem.model.SellerStatus;
import com.example.ratingsystem.model.User;
public class SecurityUtil {
    private SecurityUtil() {}

    public static void validateApprovedSeller(User user) {
        if (user.getSellerStatus() != SellerStatus.APPROVED) {
            throw new BadRequestException("Seller is not approved");
        }
    }
}
