package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.UserResponse;
import com.example.ratingsystem.exception.ResourceNotFoundException;
import com.example.ratingsystem.model.SellerStatus;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSellerServiceImpl implements AdminSellerService {

    private final UserRepository userRepository;

    @Override
    public UserResponse approveSeller(Long sellerId) {
        User user = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        user.setSellerStatus(SellerStatus.APPROVED);
        userRepository.save(user);

        return mapToUserResponse(user);
    }

    @Override
    public UserResponse rejectSeller(Long sellerId) {
        User user = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        user.setSellerStatus(SellerStatus.REJECTED);
        userRepository.save(user);

        return mapToUserResponse(user);
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .sellerStatus(user.getSellerStatus().name())
                .build();
    }
}
