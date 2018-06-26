package com.example.sonia.sonia.config;

import com.example.sonia.sonia.repository.VideoGamesRepository;
import com.example.sonia.sonia.service.VideoGamesPulling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class Test {

    @Autowired
    private VideoGamesPulling videoGamesPulling;

    @Autowired
    private VideoGamesRepository videoGamesRepository;

    @Bean
    public Object object() throws IOException {
        videoGamesRepository.saveAll(videoGamesPulling.getAllGames());
        return new Object();
    }
}
