package ru.platik777.telegramapi.TelegramCommandsChain;

import org.FitBot.DtoTrackInfo;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.platik777.service.services.TrackService;
import ru.platik777.service.services.UserService;
import ru.platik777.service.services.UserTrackRouteService;
import ru.platik777.telegramapi.FitFileParser;

public class DocumentMessageHandler extends Handler {
    private final FitFileParser parser;
    private final TrackService trackService;
    private final UserService userService;
    private final UserTrackRouteService userTrackRouteService;

    public DocumentMessageHandler(TelegramLongPollingBot bot, FitFileParser parser, TrackService trackService, UserService userService, UserTrackRouteService userTrackRouteService) {
        super(bot);
        this.parser = parser;
        this.trackService = trackService;
        this.userService = userService;
        this.userTrackRouteService = userTrackRouteService;
    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasDocument()) {
            if (update.getMessage().getDocument().getFileName().endsWith(".fit")) {
                Long chat_id = update.getMessage().getChatId();
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chat_id));
                message.setText("Спасибо вам за маршрут, это помогает нам стать лучше");
                try {
                    bot.execute(message);
                    DtoTrackInfo trackInfo = parser.parse("downloads\\"+update.getMessage().toString());
                    trackService.saveTrack(trackInfo);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (next != null) {
            next.handle(update);
        }
    }
}
