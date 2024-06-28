package ru.platik777.telegramapi;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;

public class TelegramApiApplication {

    public static void main(String[] args) {
        // Create downloads directory if it doesn't exist
        File downloadsDir = new File("downloads");
        if (!downloadsDir.exists()) {
            downloadsDir.mkdir();
        }

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new FileBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
