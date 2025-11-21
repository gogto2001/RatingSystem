package com.example.ratingsystem.service;

import com.example.ratingsystem.dto.GameObjectCreateRequest;
import com.example.ratingsystem.dto.GameObjectResponse;

import java.util.List;


public interface GameObjectService {
    void createObject(Long sellerId, GameObjectCreateRequest request);

    List<GameObjectResponse> getObjectsForSeller(Long sellerId);

    GameObjectResponse getObjectById(Long objectId);
}
