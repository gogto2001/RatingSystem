package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.GameObjectCreateRequest;
import com.example.ratingsystem.dto.GameObjectResponse;

import java.util.List;


public interface GameObjectService {
    GameObjectResponse createGameObject(GameObjectCreateRequest request);

    GameObjectResponse updateGameObject(Long id, GameObjectCreateRequest request);

    void deleteGameObject(Long id, Long sellerId);

    List<GameObjectResponse> getObjectsBySeller(Long sellerId);
}
