package com.example.sonia.sonia.bot;

import com.example.sonia.sonia.handler.CallbackQueryHandler;
import com.example.sonia.sonia.handler.UpdateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;
import javax.annotation.PostConstruct;

@Component
public class Sonia extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sonia.class);

    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;

    @Autowired
    private List<UpdateHandler> updateHandlers;

    @Autowired
    private List<CallbackQueryHandler> callbackQueryHandlers;


    static {
          ApiContextInitializer.init();
    }

    @PostConstruct
    public void registerBot(){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            LOGGER.error("Start up failed: ", e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasCallbackQuery()){
               callbackQueryHandlers.forEach(callbackHandler ->{
                   if(callbackHandler.isApplicable(update)){
                       callbackHandler.handle(update,this);
                   }
                });
        } else {
                updateHandlers.forEach(handler -> {
                    if (handler.isApplicable(update)){
                        handler.handle(update,this);
                    }
                });
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
