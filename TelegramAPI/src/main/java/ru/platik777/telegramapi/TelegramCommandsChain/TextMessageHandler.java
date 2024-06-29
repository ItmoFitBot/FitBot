package ru.platik777.telegramapi.TelegramCommandsChain;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TextMessageHandler extends Handler {
    public TextMessageHandler(TelegramLongPollingBot bot) {
        super(bot);
    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            Long chat_id = update.getMessage().getChatId();
            if (message_text.equals("/start")) {
                SendMessage message = new SendMessage();
                message.setText("Привет! Мы рады, что вы решили воспользоваться нашим ботом. Для того, чтобы начать, загрузите файл с расширением .fit и выберите, что вы хотите из него узнать");
                message.setChatId(String.valueOf(chat_id));
                try {
                    bot.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (next != null) {
            next.handle(update);
        }
    }
}