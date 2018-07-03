package com.example.sonia.sonia.handler;

import com.example.sonia.sonia.util.Tokenizer;
import org.telegram.telegrambots.api.objects.Update;

public interface UpdateHandler extends Handler {

    default boolean isApplicable(Update update) {
        return Tokenizer.tokenize(update.getMessage().getText()).containsAll(getApplicWords());
    }


}
