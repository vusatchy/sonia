package com.example.sonia.sonia.service.impl;

import com.example.sonia.sonia.model.VideoGame;
import com.example.sonia.sonia.repository.VideoGamesRepository;
import com.example.sonia.sonia.service.ScheduledUpdator;
import com.example.sonia.sonia.service.VideoGamesPull;
import com.example.sonia.sonia.util.TaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;

@Service
public class ScheduledUpdatorService implements ScheduledUpdator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledUpdatorService.class);

    @Autowired
    private VideoGamesPull videoGamesPull;

    @Autowired
    private VideoGamesRepository videoGamesRepository;

    @Value("${update.period}")
    private Integer period;

    @PostConstruct
    private void init() {
        update();
        TaskScheduler.schedule(this::update, period, period, TimeUnit.MINUTES);
    }

    @Override
    public Runnable task() {
        return this::update;
    }

    private void update() {
        try {
            Set<VideoGame> videoGames = new HashSet<>(videoGamesPull.getAllGames());
            videoGames = extractNewGames(videoGames);
            if (!videoGames.isEmpty()) {
                videoGamesRepository.saveAll(videoGames);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<VideoGame> extractNewGames(Set<VideoGame> videoGames) {
        Set<VideoGame> videoGamesFromBase = new HashSet<>();
        videoGamesRepository.findAll().forEach(videoGamesFromBase::add);
        videoGames.removeIf(game -> videoGamesFromBase.stream()
            .anyMatch(bgame -> ObjectUtils.nullSafeEquals(bgame.getHref(), game.getHref())));
        LOGGER.info("updating with {} games ", videoGames.size());
        return videoGames;
    }


}
