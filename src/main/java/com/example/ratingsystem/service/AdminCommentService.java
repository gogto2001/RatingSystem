package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.CommentResponse;

import java.util.List;

public interface AdminCommentService {

    CommentResponse approveComment(Long commentId);

    List<CommentResponse> getPendingComments();

    void rejectComment(Long commentId);
}
