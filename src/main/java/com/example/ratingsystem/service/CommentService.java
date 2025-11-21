package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.CommentCreateRequest;
import com.example.ratingsystem.dto.CommentResponse;

import java.util.List;

public interface CommentService {

    void addComment(Long sellerId, CommentCreateRequest request);

    List<CommentResponse> getApprovedComments(Long sellerId);
}