package ru.platik777.telegramapi;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.platik777.telegramapi.TelegramCommandsChain.Handler;

public class FileBot extends TelegramLongPollingBot {
    private Handler handlerChain;

    @Override
    public String getBotUsername() {
        return "HuiPppizbot";
    }

    @Override
    public String getBotToken() {
        return "7402107503:AAEVRQJmSMB0Lfegzeug_U6eS6zItCu4XuM";
    }

    @Override
    public void onUpdateReceived(Update update) {
        handlerChain.handle(update);
    }
}
