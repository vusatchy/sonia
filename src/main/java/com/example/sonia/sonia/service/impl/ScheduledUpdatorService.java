package com.example.sonia.sonia.service.impl;

import com.example.sonia.sonia.bot.Sonia;
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
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

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

    @Autowired
    private Sonia sonia;

    @Value("${update.period}")
    private Integer period;

    @Value("${chat.id}")
    private String chatId;

    @PostConstruct
    private void init() {
        update(true);
        TaskScheduler.schedule(this::task, period, period, TimeUnit.MINUTES);
    }

    @Override
    public Runnable task() {
        return () -> update(false);
    }

    private void update(boolean isInitial) {
        try {
            Set<VideoGame> videoGames = new HashSet<>(videoGamesPull.getAllGames());
            videoGames = extractNewGames(videoGames);
            if (!videoGames.isEmpty()) {
                if (!isInitial) {
                    updateBot(videoGames);
                }
                videoGamesRepository.saveAll(videoGames);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBot(Set<VideoGame> videoGames) {
        videoGames.forEach(game -> {
            SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(gameToText(game));
            try {
                sonia.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });

    }

    private String gameToText(VideoGame game) {
        StringBuilder stringBuilder = new StringBuilder()
            .append("Name: ")
            .append(game.getName())
            .append("\n")
            .append("Price: ")
            .append(game.getPrice())
            .append("\n")
            .append("Href: ")
            .append(game.getHref());
        return stringBuilder.toString();
    }

    private Set<VideoGame> extractNewGames(Set<VideoGame> videoGames) {
        Set<VideoGame> videoGamesFromBase = new HashSet<>();
        videoGamesRepository.findAll().forEach(videoGamesFromBase::add);
        videoGames.removeIf(game -> videoGamesFromBase.stream()
            .anyMatch(bgame -> ObjectUtils.nullSafeEquals(bgame.getName(), game.getName())
                               && ObjectUtils.nullSafeEquals(bgame.getPrice(), game.getPrice())));
        LOGGER.info("updating with {} games ", videoGames.size());
        return videoGames;
    }


}
