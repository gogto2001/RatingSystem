package com.example.ratingsystem.repository;

import com.example.ratingsystem.model.Role;
import com.example.ratingsystem.model.SellerStatus;
import com.example.ratingsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<User> findByRoleAndSellerStatusOrderByAverageRatingDesc(
            Role role,
            SellerStatus sellerStatus,
            Pageable pageable
    );
}
