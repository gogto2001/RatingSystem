package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.CommentResponse;
import com.example.ratingsystem.exception.ResourceNotFoundException;
import com.example.ratingsystem.model.Comment;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.CommentRepository;
import com.example.ratingsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class AdminCommentServiceImplTest {

    private final CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private final AdminCommentServiceImpl adminService =
            new AdminCommentServiceImpl(commentRepository, userRepository);

    @Test
    void approveComment_updatesSellerRating() {
        // Arrange
        User seller = new User();
        seller.setId(1L);
        seller.setRatingSum(10);
        seller.setRatingCount(2);

        Comment comment = new Comment();
        comment.setId(5L);
        comment.setRating(5);
        comment.setSeller(seller);
        comment.setApproved(false);

        Mockito.when(commentRepository.findById(5L)).thenReturn(java.util.Optional.of(comment));
        Mockito.when(commentRepository.save(any(Comment.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        CommentResponse response = adminService.approveComment(5L);

        // Assert
        assertTrue(response.isApproved());
        assertEquals(15, seller.getRatingSum());
        assertEquals(3, seller.getRatingCount());
        assertEquals(5.0, seller.getAverageRating());
    }

    @Test
    void approveComment_notFound() {
        Mockito.when(commentRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> adminService.approveComment(99L));
    }
}
