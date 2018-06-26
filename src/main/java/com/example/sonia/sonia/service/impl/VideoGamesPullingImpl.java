package com.example.sonia.sonia.service.impl;

import com.example.sonia.sonia.model.VideoGame;
import com.example.sonia.sonia.parse.HtmlPageToGameCollector;
import com.example.sonia.sonia.parse.UrlCreator;
import com.example.sonia.sonia.service.VideoGamesPulling;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoGamesPullingImpl implements VideoGamesPulling {

    @Value("${app.city}")
    private String city;

    @Override
    public List<VideoGame> getAllGames() throws IOException {
        List<VideoGame> videoGames = new ArrayList<>();
        int page = 1;
        String url = UrlCreator.buildUrl(city, page);
        while (UrlCreator.isValid(url)) {
            videoGames.addAll(HtmlPageToGameCollector.parseGames(url));
            page++;
            url = UrlCreator.buildUrl(city, page);
        }
        return HtmlPageToGameCollector.validateGames(videoGames);
    }

}
