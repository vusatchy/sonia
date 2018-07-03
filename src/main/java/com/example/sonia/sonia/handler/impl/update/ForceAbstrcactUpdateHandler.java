package com.example.sonia.sonia.handler.impl.update;

import com.example.sonia.sonia.handler.UpdateHandler;
import com.example.sonia.sonia.service.ScheduledUpdator;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultAbsSender;

import java.util.Set;

@Service
public class ForceAbstrcactUpdateHandler implements UpdateHandler {

    @Autowired
    private ScheduledUpdator scheduledUpdator;


    @Override
    public void handle(Update update, DefaultAbsSender sender) {
        scheduledUpdator.task();
    }

    @Override
    public Set<String> getApplicWords() {
        return ImmutableSet.of("соня", "оновись");
    }
}
