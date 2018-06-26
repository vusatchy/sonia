package com.example.sonia.sonia.repository;

import com.example.sonia.sonia.model.VideoGame;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoGamesRepository extends CrudRepository<VideoGame, Integer> {

    List<VideoGame> findByNameContainingIgnoreCase(String name);
}
