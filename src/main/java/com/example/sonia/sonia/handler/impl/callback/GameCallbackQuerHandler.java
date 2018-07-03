package com.example.sonia.sonia.handler.impl.callback;

import com.example.sonia.sonia.handler.CallbackQueryHandler;
import com.example.sonia.sonia.model.VideoGame;
import com.example.sonia.sonia.repository.VideoGamesRepository;
import com.example.sonia.sonia.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameCallbackQuerHandler implements CallbackQueryHandler {


    @Value("#{'${app.games}'.split(',')}")
    private List<String> games;

    @Autowired
    private VideoGamesRepository videoGamesRepository;

    private final static String NO_RESULTS_MESSAGE = "No results";

    @Override
    public void handle(Update update, DefaultAbsSender sender) {
        List<VideoGame> games = videoGamesRepository
            .findByNameContainingIgnoreCaseOrderByPriceDesc(update.getCallbackQuery().getData());

        List<String> messages = games.stream().map(Convertor::gameToText).collect(Collectors.toList());

        if(messages.isEmpty()){
            SendMessage sendMessage = new SendMessage()
                .setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setText(NO_RESULTS_MESSAGE);

            try {
                sender.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }else {
            messages.forEach(message -> {
                SendMessage sendMessage = new SendMessage()
                    .setChatId(update.getCallbackQuery().getMessage().getChatId())
                    .setText(message);

                try {
                    sender.execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public Set<String> getApplicWords() {
        return new HashSet<>(games);
    }
}
