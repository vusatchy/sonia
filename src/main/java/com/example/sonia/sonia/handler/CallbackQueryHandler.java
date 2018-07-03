package com.example.sonia.sonia.handler;

import org.telegram.telegrambots.api.objects.Update;

public interface CallbackQueryHandler extends Handler {

    default boolean isApplicable(Update update) {
        return getApplicWords().contains(update.getCallbackQuery().getData());
    }

}
