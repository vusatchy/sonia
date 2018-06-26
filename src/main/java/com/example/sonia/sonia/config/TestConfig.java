package com.example.sonia.sonia.config;

import com.example.sonia.sonia.model.VideoGame;
import com.example.sonia.sonia.parse.HtmlPageToGameCollector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Configuration
public class TestConfig {

    @Value("${app.city}")
    private String city;

    @Bean
    public Object object() throws IOException {
        String url = "https://www.olx.ua/uk/elektronika/igry-i-igrovye-pristavki/igry-dlya-pristavok/sony-playstation/"
                     + city
                     + "/q-ps4/";
        List<VideoGame> videoGames = HtmlPageToGameCollector.parseGames(url);
        videoGames = HtmlPageToGameCollector.validateGames(videoGames);
        videoGames.forEach(System.out::println);
        return new Object();
    }
}
