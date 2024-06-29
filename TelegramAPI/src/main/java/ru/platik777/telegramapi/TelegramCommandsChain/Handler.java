package ru.platik777.telegramapi.TelegramCommandsChain;

import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Handler {
    @Setter
    protected Handler next;
    protected TelegramLongPollingBot bot;

    public Handler(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    public abstract void handle(Update update);
}

