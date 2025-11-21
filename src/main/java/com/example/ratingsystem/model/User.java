package com.example.ratingsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private int ratingSum = 0;

    @Column(nullable = false)
    private int ratingCount = 0;

    @Column(nullable = false)
    private double averageRating = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SellerStatus sellerStatus = SellerStatus.PENDING;

    private boolean verified = false;

    private LocalDateTime createdAt = LocalDateTime.now();


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
