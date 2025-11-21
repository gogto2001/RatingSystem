package com.example.ratingsystem.service;
import com.example.ratingsystem.dto.CommentResponse;
import com.example.ratingsystem.exception.ResourceNotFoundException;
import com.example.ratingsystem.model.Comment;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.CommentRepository;
import com.example.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService{
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public List<CommentResponse> getNotApprovedComments() {

        List<Comment> comments = commentRepository.findAllByApprovedFalse();

        return comments.stream()
                .map(c -> CommentResponse.builder()
                        .id(c.getId())
                        .message(c.getMessage())
                        .authorId(c.getAuthorId())
                        .createdAt(c.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public CommentResponse approveComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setApproved(true);

        commentRepository.save(comment);

        User seller = comment.getSeller();
        int rating = comment.getRating();

        seller.setRatingSum(seller.getRatingSum() + rating);
        seller.setRatingCount(seller.getRatingCount() + 1);

        double avg = (double) seller.getRatingSum() / seller.getRatingCount();
        seller.setAverageRating(avg);

        userRepository.save(seller);

        return mapToCommentResponse(comment);
    }

    @Override
    public List<CommentResponse> getPendingComments() {
        List<Comment> comments = commentRepository.findAllByApprovedFalse();

        return comments.stream()
                .map(this::mapToCommentResponse)
                .toList();
    }

    @Override
    public void rejectComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }


    private CommentResponse mapToCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getMessage(),
                comment.getRating(),
                comment.isApproved(),
                comment.getAuthorId(),
                comment.getCreatedAt()
        );
    }

}
