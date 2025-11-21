package com.example.ratingsystem.service;


import com.example.ratingsystem.dto.LoginRequest;
import com.example.ratingsystem.dto.LoginResponse;
import com.example.ratingsystem.dto.RegisterRequest;
import com.example.ratingsystem.dto.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    UserResponse getUserById(Long id);


}
