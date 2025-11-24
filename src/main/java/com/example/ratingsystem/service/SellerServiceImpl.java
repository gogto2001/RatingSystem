package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.SellerStatisticsResponse;
import com.example.ratingsystem.exception.ResourceNotFoundException;
import com.example.ratingsystem.model.Comment;
import com.example.ratingsystem.model.GameObject;
import com.example.ratingsystem.model.Role;
import com.example.ratingsystem.model.SellerStatus;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.CommentRepository;
import com.example.ratingsystem.repository.GameObjectRepository;
import com.example.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + sellerId));


        if (seller.getRole() != Role.SELLER) {
            throw new ResourceNotFoundException("User with id " + sellerId + " is not a seller");
        }


        List<Comment> comments = commentRepository.findAllBySeller(seller);

        int totalComments = comments.size();
        int approvedComments = (int) comments.stream()
                .filter(Comment::isApproved)
                .count();


        List<GameObject> gameObjects = gameObjectRepository.findAllBySeller(seller);
        int gameObjectCount = gameObjects.size();



        return new SellerStatisticsResponse(
                seller.getId(),
                seller.getFirstName(),
                seller.getAverageRating(),
                seller.getRatingCount(),
                seller.getRatingSum(),
                approvedComments,
                totalComments,
                gameObjectCount
        );
    }

    @Override
    public List<SellerStatisticsResponse> getTopSellers(int limit) {

        Page<User> page = userRepository.findByRoleAndSellerStatusOrderByAverageRatingDesc(
                Role.SELLER,
                SellerStatus.APPROVED,
                PageRequest.of(0, limit)
        );

        List<User> sellers = page.getContent();


        return sellers.stream()
                .map(this::mapToStatistics)
                .toList();
    }



    private SellerStatisticsResponse mapToStatistics(User seller) {


        List<Comment> comments = commentRepository.findAllBySeller(seller);
        int totalComments = comments.size();
        int approvedComments = (int) comments.stream()
                .filter(Comment::isApproved)
                .count();


        List<GameObject> gameObjects = gameObjectRepository.findAllBySeller(seller);
        int gameObjectCount = gameObjects.size();

        return new SellerStatisticsResponse(
                seller.getId(),
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
