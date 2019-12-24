package com.example.sonia.sonia.handler.impl.update;

import com.example.sonia.sonia.handler.UpdateHandler;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class PreferedGamesAbstrcactUpdateHandler implements UpdateHandler {

    private final static int ROWS = 3;

    @Value("#{'${app.items}'.split(',')}")
    private List<String> games;

    @Override
    public void handle(Update update, DefaultAbsSender sender) {
        SendMessage message = new SendMessage() // Create a message object object
            .setChatId(update.getMessage().getChatId())
            .setText("Items:");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        games.forEach(game -> {
            rowInline.add(new InlineKeyboardButton().setText(game).setCallbackData(game));
        });
        List<List<InlineKeyboardButton>> rowsInline = Lists.partition(rowInline, ROWS);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getApplicWords() {
        return ImmutableSet.of("соня", "список");
    }
}
