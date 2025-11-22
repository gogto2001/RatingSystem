package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.GameObjectCreateRequest;
import com.example.ratingsystem.dto.GameObjectResponse;
import com.example.ratingsystem.exception.BadRequestException;
import com.example.ratingsystem.exception.ResourceNotFoundException;
import com.example.ratingsystem.model.GameObject;
import com.example.ratingsystem.model.Role;
import com.example.ratingsystem.model.SellerStatus;
import com.example.ratingsystem.model.User;
import com.example.ratingsystem.repository.GameObjectRepository;
import com.example.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GameObjectServiceImpl implements GameObjectService {

    private final GameObjectRepository gameObjectRepository;
    private final UserRepository userRepository;

    @Override
    public GameObjectResponse createGameObject(GameObjectCreateRequest request) {

        // 1) მოვძებნოთ seller
        User seller = userRepository.findById(request.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        // 2) შევამოწმოთ რომ მართლაც SELLER-ია და APPROVED
        validateSeller(seller);

        // 3) შევქმნათ game object
        GameObject game = GameObject.builder()
                .title(request.getTitle())
                .text(request.getText())
                .seller(seller)
                .build();

        GameObject saved = gameObjectRepository.save(game);

        return toResponse(saved);
    }

    @Override
    public GameObjectResponse updateGameObject(Long id, GameObjectCreateRequest request) {

        GameObject game = gameObjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GameObject not found"));

        User seller = userRepository.findById(request.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        // game object-ის შეცვლა მხოლოდ ავტორს შეუძლია
        if (!Objects.equals(game.getSeller().getId(), seller.getId())) {
            throw new BadRequestException("Only the owner can update this object");
        }

        validateSeller(seller);

        game.setTitle(request.getTitle());
        game.setText(request.getText());

        GameObject saved = gameObjectRepository.save(game);

        return toResponse(saved);
    }

    @Override
    public void deleteGameObject(Long id, Long sellerId) {

        GameObject game = gameObjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GameObject not found"));

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        // მხოლოდ ავტორს შეუძლია წაშლა
        if (!Objects.equals(game.getSeller().getId(), seller.getId())) {
            throw new BadRequestException("Only the owner can delete this object");
        }

        validateSeller(seller);

        gameObjectRepository.delete(game);
    }

    @Override
    public List<GameObjectResponse> getObjectsBySeller(Long sellerId) {

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        List<GameObject> objects = gameObjectRepository.findAllBySeller(seller);

        return objects.stream()
                .map(this::toResponse)
                .toList();
    }

    // =================== private helper methods =========================

    private void validateSeller(User seller) {
        if (seller.getRole() != Role.SELLER) {
            throw new BadRequestException("User is not a seller");
        }

        if (seller.getSellerStatus() != SellerStatus.APPROVED) {
            throw new BadRequestException("Seller is not approved");
        }
    }

    private GameObjectResponse toResponse(GameObject game) {
        return GameObjectResponse.builder()
                .id(game.getId())
                .title(game.getTitle())
                .text(game.getText())
                .sellerId(game.getSeller().getId())
                .createdAt(game.getCreatedAt())
                .updatedAt(game.getUpdatedAt())
                .build();
    }
}
