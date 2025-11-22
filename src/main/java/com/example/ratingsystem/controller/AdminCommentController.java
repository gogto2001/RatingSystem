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
        List<CommentResponse> responses = adminCommentService.getPendingComments();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{commentId}/approve")
    public ResponseEntity<CommentResponse> approveComment(@PathVariable Long commentId) {
        CommentResponse response = adminCommentService.approveComment(commentId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{commentId}/reject")
    public ResponseEntity<Void> rejectComment(@PathVariable Long commentId) {
        adminCommentService.rejectComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
