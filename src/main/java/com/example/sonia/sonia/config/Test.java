package com.example.sonia.sonia.config;

import com.example.sonia.sonia.repository.VideoGamesRepository;
import com.example.sonia.sonia.service.VideoGamesPull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashSet;

@Configuration
public class Test {

    @Autowired
    private VideoGamesPull videoGamesPull;

    @Autowired
    private VideoGamesRepository videoGamesRepository;

    @Bean
    public Object object() throws IOException {
        videoGamesRepository.saveAll(new HashSet<>(videoGamesPull.getAllGames()));
        return new Object();
    }
}
