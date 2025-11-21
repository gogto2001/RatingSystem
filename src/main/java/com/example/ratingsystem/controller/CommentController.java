package com.example.ratingsystem.controller;

import com.example.ratingsystem.dto.CommentCreateRequest;
import com.example.ratingsystem.dto.CommentResponse;
import com.example.ratingsystem.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/seller/{sellerId}")
    public ResponseEntity<Void> addComment(
            @PathVariable @Valid Long sellerId,
            @RequestBody CommentCreateRequest request) {

        commentService.addComment(sellerId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<CommentResponse>> getApprovedComments(
            @PathVariable Long sellerId) {

        List<CommentResponse> comments = commentService.getApprovedComments(sellerId);
        return ResponseEntity.ok(comments);
    }
}
