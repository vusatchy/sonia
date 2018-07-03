package com.example.sonia.sonia.handler;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultAbsSender;

import java.util.Set;

public interface Handler {

    boolean isApplicable(Update update);

    void handle(Update update, DefaultAbsSender sender);

    Set<String> getApplicWords();
}
