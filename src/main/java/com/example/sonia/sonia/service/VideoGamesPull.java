package com.example.sonia.sonia.service;

import com.example.sonia.sonia.model.VideoGame;

import java.io.IOException;
import java.util.List;

public interface VideoGamesPull {

    List<VideoGame> getAllGames() throws IOException;
}