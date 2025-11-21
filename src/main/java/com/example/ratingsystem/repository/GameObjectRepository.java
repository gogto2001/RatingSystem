package com.example.ratingsystem.repository;

import com.example.ratingsystem.model.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ratingsystem.model.User;


import java.util.List;

public interface GameObjectRepository extends JpaRepository<GameObject, Long> {
    List<GameObject> findAllBySeller(User seller);


    List<GameObject> findAllByTitleContainingIgnoreCase(String keyword);
}
