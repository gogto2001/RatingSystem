package com.example.ratingsystem.controller;

import com.example.ratingsystem.dto.CommentResponse;
import com.example.ratingsystem.service.AdminCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {
    private final AdminCommentService adminCommentService;


    @GetMapping("/pending")
    public ResponseEntity<List<CommentResponse>> getPendingComments() {
        List<CommentResponse> comments = adminCommentService.getNotApprovedComments();
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{commentId}/approve")
    public ResponseEntity<Void> approveComment(@PathVariable Long commentId) {
        adminCommentService.approveComment(commentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public void rejectComment(@PathVariable Long commentId) {
        adminCommentService.rejectComment(commentId);
    }

}
