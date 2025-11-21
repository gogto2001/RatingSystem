package com.example.ratingsystem.service;


import com.example.ratingsystem.dto.SellerStatisticsResponse;
import com.example.ratingsystem.exception.ResourceNotFoundException;
import com.example.ratingsystem.model.Comment;
import com.example.ratingsystem.model.GameObject;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.CommentRepository;
import com.example.ratingsystem.repository.GameObjectRepository;
import com.example.ratingsystem.repository.UserRepository;
import com.example.ratingsystem.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final GameObjectRepository gameObjectRepository;

    @Override
    public SellerStatisticsResponse getSellerStatistics(Long sellerId) {

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        List<Comment> allComments = commentRepository.findAll();
        List<GameObject> allObjects = gameObjectRepository.findAll();

        int totalComments = 0;
        int approvedComments = 0;
        int gameObjectCount = 0;

        for (Comment comment : allComments) {
            if (comment.getSeller().getId().equals(sellerId)) {
                totalComments++;
                if (comment.isApproved()) approvedComments++;
            }
        }

        for (GameObject obj : allObjects) {
            if (obj.getSeller().getId().equals(sellerId)) {
                gameObjectCount++;
            }
        }

        return new SellerStatisticsResponse(
                sellerId,
                seller.getFirstName(),
                seller.getAverageRating(),
                seller.getRatingCount(),
                seller.getRatingSum(),
                approvedComments,
                totalComments,
                gameObjectCount
        );
    }
}