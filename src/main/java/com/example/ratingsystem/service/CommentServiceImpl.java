package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.CommentCreateRequest;
import com.example.ratingsystem.dto.CommentResponse;
import com.example.ratingsystem.exception.BadRequestException;
import com.example.ratingsystem.exception.ResourceNotFoundException;
import com.example.ratingsystem.model.Comment;
import com.example.ratingsystem.model.Role;
import com.example.ratingsystem.model.SellerStatus;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.CommentRepository;
import com.example.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public CommentResponse createComment(Long sellerId, CommentCreateRequest request) {
        // 1) მოვძებნოთ სელერი
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + sellerId));

        // 2) შევამოწმოთ რომ ის რეალურად SELLER-ია და APPROVED
        if (seller.getRole() != Role.SELLER) {
            throw new BadRequestException("User with id " + sellerId + " is not a seller");
        }

        if (seller.getSellerStatus() != SellerStatus.APPROVED) {
            throw new BadRequestException("Seller with id " + sellerId + " is not approved");
        }

        // 3) მარტივი ვალიდაცია rating-ზე
        if (request.getRating() == null || request.getRating() < 1 || request.getRating() > 5) {
            throw new BadRequestException("Rating must be between 1 and 5");
        }

        if (request.getAuthorId() == null) {
            throw new BadRequestException("AuthorId is required");
        }

        // 4) შევქმნათ Comment ენტიტი
        Comment comment = Comment.builder()
                .message(request.getMessage())
                .rating(request.getRating())
                .authorId(request.getAuthorId())
                .seller(seller)
                .approved(false) // admin-ს უნდა დაადასტუროს
                .build();

        Comment saved = commentRepository.save(comment);

        // 5) დავაბრუნოთ DTO
        return mapToCommentResponse(saved);
    }

    @Override
    public List<CommentResponse> getApprovedCommentsForSeller(Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + sellerId));

        List<Comment> comments = commentRepository.findAllBySellerAndApprovedTrue(seller);

        return comments.stream()
                .map(this::mapToCommentResponse)
                .toList();
    }

    // ================== private helper ==================

    private CommentResponse mapToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .message(comment.getMessage())
                .rating(comment.getRating())
                .authorId(comment.getAuthorId())
                .sellerId(comment.getSeller().getId())
                .approved(comment.isApproved())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
