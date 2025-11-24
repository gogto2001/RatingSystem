package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.LoginRequest;
import com.example.ratingsystem.dto.LoginResponse;
import com.example.ratingsystem.dto.RegisterRequest;
import com.example.ratingsystem.dto.UserResponse;
import com.example.ratingsystem.exception.BadRequestException;
import com.example.ratingsystem.exception.ResourceNotFoundException;
import com.example.ratingsystem.model.Role;
import com.example.ratingsystem.model.SellerStatus;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("User with this email already exists");
        }


        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.SELLER)
                .sellerStatus(SellerStatus.PENDING)
                .ratingSum(0)
                .ratingCount(0)
                .averageRating(0.0)
                .verified(false)
                .build();

        User saved = userRepository.save(user);

        return mapToUserResponse(saved);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));


        validatePassword(request.getPassword(), user.getPassword());


        return mapToLoginResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return mapToUserResponse(user);
    }

    

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BadRequestException("Invalid email or password");
        }
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .sellerStatus(user.getSellerStatus().name())
                .build();
    }

    private LoginResponse mapToLoginResponse(User user) {
        return LoginResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("Login successful")
                .build();
    }
}
