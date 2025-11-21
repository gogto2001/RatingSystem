package com.example.ratingsystem.repository;

import com.example.ratingsystem.model.Comment;
import com.example.ratingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllBySeller(User seller);
    List<Comment> findAllBySellerAndApprovedTrue(User seller);
    List<Comment> findAllByApprovedFalse();
}
