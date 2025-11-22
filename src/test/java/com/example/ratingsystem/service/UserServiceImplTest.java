package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.RegisterRequest;
import com.example.ratingsystem.dto.UserResponse;
import com.example.ratingsystem.exception.BadRequestException;
import com.example.ratingsystem.model.Role;
import com.example.ratingsystem.model.SellerStatus;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class UserServiceImplTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    private final UserServiceImpl userService =
            new UserServiceImpl(userRepository, passwordEncoder);

    @Test
    void register_createsPendingSeller() {
        // Arrange
        RegisterRequest req = new RegisterRequest();
        req.setEmail("test@mail.com");
        req.setPassword("pass");
        req.setFirstName("Gio");
        req.setLastName("Tsetskhladze");

        Mockito.when(userRepository.existsByEmail("test@mail.com")).thenReturn(false);
        Mockito.when(passwordEncoder.encode("pass")).thenReturn("encoded");
        Mockito.when(userRepository.save(any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        // Act
        UserResponse response = userService.register(req);

        // Assert
        assertEquals("test@mail.com", response.getEmail());
        assertEquals("SELLER", response.getRole());
        assertEquals("PENDING", response.getSellerStatus());
    }

    @Test
    void register_duplicateEmail_throwsException() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("duplicate@mail.com");

        Mockito.when(userRepository.existsByEmail("duplicate@mail.com")).thenReturn(true);

        assertThrows(BadRequestException.class,
                () -> userService.register(req));
    }
}
