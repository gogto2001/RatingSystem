package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.CommentCreateRequest;
import com.example.ratingsystem.dto.CommentResponse;
import com.example.ratingsystem.exception.BadRequestException;
import com.example.ratingsystem.model.Comment;
import com.example.ratingsystem.model.SellerStatus;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.CommentRepository;
import com.example.ratingsystem.repository.UserRepository;
import com.example.ratingsystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public void addComment(Long sellerId, CommentCreateRequest request) {

        // 1) იპოვე seller
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        if (seller.getSellerStatus() != SellerStatus.APPROVED) {
            throw new BadRequestException("Seller is not approved");
        }

        // 2️⃣ User cannot comment on themselves
        if (request.getAuthorId() != null && sellerId.equals(request.getAuthorId())) {
            throw new BadRequestException("You cannot comment yourself");
        }

        // 3️⃣ Rating boundaries (DTO already checks, but double-check optional)
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new BadRequestException("Rating must be between 1 and 5");
        }


        // 2) შექმენი ახალი კომენტარი
        Comment comment = Comment.builder()
                .message(request.getMessage())
                .authorId(request.getAuthorId())   // null allowed
                .seller(seller)
                .approved(false)                  // default — awaits admin approval
                .build();

        // 3) შეინახე კომენტარი
        commentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> getApprovedComments(Long sellerId) {

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        List<Comment> comments =
                commentRepository.findAllBySellerAndApprovedTrue(seller);

        // 3) Comment → CommentResponse mapping
        return comments.stream()
                .map(c -> CommentResponse.builder()
                        .id(c.getId())
                        .message(c.getMessage())
                        .authorId(c.getAuthorId())
                        .createdAt(c.getCreatedAt())
                        .build())
                .toList();
    }
}