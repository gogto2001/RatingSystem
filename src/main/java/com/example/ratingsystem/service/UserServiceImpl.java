package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.LoginRequest;
import com.example.ratingsystem.dto.LoginResponse;
import com.example.ratingsystem.dto.RegisterRequest;
import com.example.ratingsystem.dto.UserResponse;
import com.example.ratingsystem.model.Role;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {

        validateEmailNotUsed(request.getEmail());

        User user = mapToUser(request);
        userRepository.save(user);

        return mapToUserResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = findUserByEmail(request.getEmail());

        validatePassword(request.getPassword(), user.getPassword());

        return mapToLoginResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToUserResponse(user);
    }


    private void validateEmailNotUsed(String email) {
        if (userRepository.existsByEmail(email.toLowerCase())) {
            throw new RuntimeException("Email already registered");
        }
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    private User mapToUser(RegisterRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.SELLER)
                .verified(false)
                .build();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
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