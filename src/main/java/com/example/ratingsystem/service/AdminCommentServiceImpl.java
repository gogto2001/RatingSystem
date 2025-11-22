package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.CommentResponse;
import com.example.ratingsystem.exception.ResourceNotFoundException;
import com.example.ratingsystem.model.Comment;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.CommentRepository;
import com.example.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public List<CommentResponse> getPendingComments() {
        return commentRepository.findAllByApprovedFalse()
                .stream()
                .map(this::mapToCommentResponse)
                .toList();
    }

    @Override
    @Transactional
    public CommentResponse approveComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        if (comment.isApproved()) {
            // უკვე დამტკიცებულია — უბრალოდ ვაბრუნებთ
            return mapToCommentResponse(comment);
        }

        comment.setApproved(true);

        // განვაახლოთ სელერის რეიტინგი
        User seller = comment.getSeller();

        int newRatingSum = seller.getRatingSum() + comment.getRating();
        int newRatingCount = seller.getRatingCount() + 1;
        double newAverage = (double) newRatingSum / newRatingCount;

        seller.setRatingSum(newRatingSum);
        seller.setRatingCount(newRatingCount);
        seller.setAverageRating(newAverage);

        // @Transactional-ის გამო, ცალკე save Seller/Comment-ზე აუცილებელი არ არის,
        // მაგრამ თუ გინდა უფრო მკაფიოდ:
        userRepository.save(seller);
        commentRepository.save(comment);

        return mapToCommentResponse(comment);
    }

    @Override
    @Transactional
    public void rejectComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        // რეიტინგზე არაფერი არ ვკეთებთ, უბრალოდ ვშლით ან ვინახავთ როგორც rejected
        commentRepository.delete(comment);
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
