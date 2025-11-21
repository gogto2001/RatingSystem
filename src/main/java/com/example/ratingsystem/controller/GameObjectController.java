package com.example.ratingsystem.controller;
import com.example.ratingsystem.dto.GameObjectCreateRequest;
import com.example.ratingsystem.dto.GameObjectResponse;
import com.example.ratingsystem.service.GameObjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/objects")
@RequiredArgsConstructor
public class GameObjectController {
    private final GameObjectService gameObjectService;

    @PostMapping("/seller/{sellerId}")
    public ResponseEntity<Void> createObject(
            @PathVariable Long sellerId,
            @RequestBody @Valid GameObjectCreateRequest request
    ) {
        gameObjectService.createObject(sellerId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<GameObjectResponse>> getObjectsForSeller(
            @PathVariable Long sellerId
    ) {
        List<GameObjectResponse> objects = gameObjectService.getObjectsForSeller(sellerId);
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/{objectId}")
    public ResponseEntity<GameObjectResponse> getObjectById(
            @PathVariable Long objectId
    ) {
        GameObjectResponse response = gameObjectService.getObjectById(objectId);
        return ResponseEntity.ok(response);
    }
}
