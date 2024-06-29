package ru.platik777.telegramapi;

import org.FitBot.DtoTrackInfo;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.platik777.reader.FitFileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasDocument()) {
            handleDocument(update.getMessage());
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update.getMessage());
        }
    }

    private void handleDocument(Message message) {
        String chatId = message.getChatId().toString();
        String fileId = message.getDocument().getFileId();
        String fileName = message.getDocument().getFileName();

        try {
            // Get file info
            GetFile getFile = new GetFile();
            getFile.setFileId(fileId);
            File file = execute(getFile);

            // Create downloads directory if it doesn't exist
            java.io.File downloadsDir = new java.io.File("downloads");
            if (!downloadsDir.exists()) {
                downloadsDir.mkdir();
            }

            // Save file to a specific location
            Path filePath = Paths.get("downloads", fileName);
            try (InputStream inputStream = downloadFileAsStream(file)) {
                Files.copy(inputStream, filePath);
            }

            // TODO: добавить вызов Reader и сохранение данных в бд
            FitFileReader fitFileReader = new FitFileReader();
            DtoTrackInfo trackInfo = fitFileReader.read(filePath.toString());

            // Выводим прочитанные данные на консоль
            System.out.println("Прочитанные данные:");
            System.out.println("Общее расстояние: " + trackInfo.getTotalDistance());
            System.out.println("Общее время: " + trackInfo.getTotalTime());
                System.out.println("Общий подъем: " + trackInfo.getTotalElevationGain());
                System.out.println("Средний пульс: " + trackInfo.getHeartRate());
                System.out.println("Первая широта: " + trackInfo.getFirstLatitude());
                System.out.println("Первая долгота: " + trackInfo.getFirstLongitude());
                System.out.println("Последняя широта: " + trackInfo.getLastLatitude());
                System.out.println("Последняя долгота: " + trackInfo.getLastLongitude());
            Files.delete(filePath);

            // Send a message with options
            sendOptionsMessage(chatId, fileName);
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
            sendMessage(chatId, "Ошибка обработки файла: " + e.getMessage() + ", попробуйте отправить его снова");
        }
    }

    private void handleTextMessage(Message message) {
        String chatId = message.getChatId().toString();
        String text = message.getText();

        // Handle different text commands
        switch (text) {
            case "/start" -> sendMessage(chatId, "Привет! Мы рады, что вы решили воспользоваться нашим ботом. " +
                    "Для того, чтобы начать, загрузите файл с расширением .fit и выберите, что вы хотите из него узнать");
            case "Общая информация о маршруте" -> sendMessage(chatId, "Общая информация о маршруте:");

            // TODO: добавить вызов сервиса с получением общей информации
            case "Рекомендация по погоде на маршрут" ->
                    sendMessage(chatId, "Рекомендации по погоде на маршрут:");
            // TODO: добавить вызов сервиса с рекомендациями по погоде
            case "Рекомендация, исходя из прошлых маршрутов" ->
                    sendMessage(chatId, "Рекомендация, исходя из прошлых маршрутов: ");
            // TODO: добавить вызов сервиса с рекомендациями по маршруту, исходя из прошлых маршрутов
            case "Поставить оценку маршруту" ->
                    sendMessage(chatId, "Введите в одну строку через пробел оценку маршрута от 0 до 10 по данным критериям: \n" +
                            "1) Сложность маршрута \n2) Физическая нагрузка\n3) Психологическая нагрузка" +
                            "4) Ландшафтная привлекательность\n5) Безопасность\n6) Общее впечатление от маршрута");

            default -> {
                if (text.matches("(\\d+\\s){5}\\d+")) {
                    String[] ratings = text.split("\\s");
                    boolean valid = true;
                    for (String rating : ratings) {
                        int value = Integer.parseInt(rating);
                        if (value < 0 || value > 10) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        saveRating(chatId, text);
                        sendMessage(chatId, "Спасибо за вашу оценку!");
                    } else {
                        sendMessage(chatId, "Каждое значение должно быть числом от 0 до 10.");
                    }
                } else {
                    sendMessage(chatId, "Незнакомая команда или неправильный формат оценки.");
                }
            }
        }
    }

    // TODO: Изменить метод, чтобы сохранение происходило в бд
    private void saveRating(String chatId, String rating) {
        String filePath = "ratings.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(chatId + ": " + rating);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendOptionsMessage(String chatId, String fileName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Файл " + fileName + " получен. Выберите действие:");

        // Create keyboard
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true); // Make the keyboard smaller
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Общая информация о маршруте"));
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Рекомендация по погоде на маршрут"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Рекомендация, исходя из прошлых маршрутов"));
        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("Поставить оценку маршруту"));
        keyboard.add(row);
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Travel_Share_Fit_bot";
    }

    @Override
    public String getBotToken() {
        return "7426053768:AAG8CM5PmMcJmqnI1QUFQI9v9_QdJwf1oWc";
    }
}
