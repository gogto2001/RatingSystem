package com.example.ratingsystem.controller;

import com.example.ratingsystem.dto.CommentCreateRequest;
import com.example.ratingsystem.dto.CommentResponse;
import com.example.ratingsystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{sellerId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long sellerId,
            @RequestBody CommentCreateRequest request
    ) {
        CommentResponse response = commentService.createComment(sellerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getApprovedComments(
            @PathVariable Long sellerId
    ) {
        List<CommentResponse> responses = commentService.getApprovedCommentsForSeller(sellerId);
        return ResponseEntity.ok(responses);
    }
}
