package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.GameObjectCreateRequest;
import com.example.ratingsystem.dto.GameObjectResponse;
import com.example.ratingsystem.exception.BadRequestException;
import com.example.ratingsystem.model.GameObject;
import com.example.ratingsystem.model.SellerStatus;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.GameObjectRepository;
import com.example.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@RequiredArgsConstructor
public class GameObjectServiceImpl implements GameObjectService {
    private final UserRepository userRepository;
    private final GameObjectRepository gameObjectRepository;

    @Override
    public void createObject(Long sellerId, GameObjectCreateRequest request) {

        // 1) მოვძებნოთ seller (User)
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        if (seller.getSellerStatus() != SellerStatus.APPROVED) {
            throw new BadRequestException("Seller is not approved");
        }

        // 3) სიგრძის შეზღუდვები (მოითხოვება production-ში)
        if (request.getTitle().length() < 3) {
            throw new BadRequestException("Name must be at least 3 characters");
        }

        if (request.getText().length() < 5) {
            throw new BadRequestException("Description must be at least 5 characters");
        }

        // 2) შევქმნათ GameObject entity DTO-დან
        GameObject gameObject = GameObject.builder()
                .title(request.getTitle())
                .text(request.getText())
                .user(seller)
                .build();

        // 3) შევინახოთ DB-ში
        gameObjectRepository.save(gameObject);
    }

    @Override
    public List<GameObjectResponse> getObjectsForSeller(Long sellerId) {

        // 1) ვიპოვოთ seller
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        // 2) წამოვიღოთ seller-ის ყველა ობიექტი
        List<GameObject> objects = gameObjectRepository.findAllBySeller(seller);

        // 3) Entity -> DTO mapping
        return objects.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public GameObjectResponse getObjectById(Long objectId) {

        GameObject obj = gameObjectRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Object not found"));

        return mapToResponse(obj);
    }

    private GameObjectResponse mapToResponse(GameObject obj) {
        return GameObjectResponse.builder()
                .id(obj.getId())
                .title(obj.getTitle())
                .text(obj.getText())
                .createdAt(obj.getCreatedAt())
                .updatedAt(obj.getUpdatedAt())
                .build();
    }

}
