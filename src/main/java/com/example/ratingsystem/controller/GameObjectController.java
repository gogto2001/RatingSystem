package com.example.ratingsystem.controller;
import com.example.ratingsystem.dto.GameObjectCreateRequest;
import com.example.ratingsystem.dto.GameObjectResponse;
import com.example.ratingsystem.service.GameObjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/object")
public class GameObjectController {

    private final GameObjectService gameObjectService;

    // POST /object
    @PostMapping
    public ResponseEntity<GameObjectResponse> create(
            @RequestBody GameObjectCreateRequest request
    ) {
        GameObjectResponse response = gameObjectService.createGameObject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT /object/{id}
    @PutMapping("/{id}")
    public ResponseEntity<GameObjectResponse> update(
            @PathVariable Long id,
            @RequestBody GameObjectCreateRequest request
    ) {
        GameObjectResponse response = gameObjectService.updateGameObject(id, request);
        return ResponseEntity.ok(response);
    }

    // DELETE /object/{id}?sellerId=5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam Long sellerId
    ) {
        gameObjectService.deleteGameObject(id, sellerId);
        return ResponseEntity.noContent().build();
    }

    // GET /object?sellerId=5
    @GetMapping
    public ResponseEntity<List<GameObjectResponse>> getAll(
            @RequestParam Long sellerId
    ) {
        List<GameObjectResponse> list = gameObjectService.getObjectsBySeller(sellerId);
        return ResponseEntity.ok(list);
    }
}
