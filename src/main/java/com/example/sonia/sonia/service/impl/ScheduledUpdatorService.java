package com.example.sonia.sonia.service.impl;

import com.example.sonia.sonia.bot.Sonia;
import com.example.sonia.sonia.model.Item;
import com.example.sonia.sonia.repository.ItemsRepository;
import com.example.sonia.sonia.service.ScheduledUpdator;
import com.example.sonia.sonia.service.ItemsPull;
import com.example.sonia.sonia.util.Convertor;
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
    private ItemsPull itemsPull;

    @Autowired
    private ItemsRepository itemsRepository;

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

    public void task() {
        update(false);
    }

    private void update(boolean isInitial) {
        try {
            Set<Item> items = new HashSet<>(itemsPull.getAllGames());
            items = extractNewItems(items);
            if (!items.isEmpty()) {
                if (!isInitial) {
                    updateBot(items);
                }
                itemsRepository.saveAll(items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBot(Set<Item> items) {
        items.forEach(game -> {
            SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(Convertor.gameToText(game));
            try {
                sonia.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });

    }



    private Set<Item> extractNewItems(Set<Item> items) {
        Set<Item> videoGamesFromBase = new HashSet<>();
        itemsRepository.findAll().forEach(videoGamesFromBase::add);
        items.removeIf(game -> videoGamesFromBase.stream()
            .anyMatch(bgame -> ObjectUtils.nullSafeEquals(bgame.getName(), game.getName())
                               && ObjectUtils.nullSafeEquals(bgame.getPrice(), game.getPrice())));
        LOGGER.info("updating with {} items ", items.size());
        return items;
    }


}
