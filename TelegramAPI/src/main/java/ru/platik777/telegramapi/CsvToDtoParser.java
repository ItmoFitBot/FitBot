package ru.platik777.telegramapi;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.FitBot.DtoTrackInfo;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CsvToDtoParser {

    public DtoTrackInfo parse(String csvFilePath) {
        DtoTrackInfo trackInfo = null;
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if ("Data".equals(nextLine[0])) {
                    double totalDistance = Double.parseDouble(nextLine[23]);
                    double totalTime = Double.parseDouble(nextLine[24]);
                    double totalElevationGain = Double.parseDouble(nextLine[25]);
                    long firstLatitude = Long.parseLong(nextLine[26]);
                    long firstLongitude = Long.parseLong(nextLine[27]);
                    long lastLatitude = Long.parseLong(nextLine[28]);
                    long lastLongitude = Long.parseLong(nextLine[29]);
                    String comment = ""; // Здесь вы можете добавить логику для получения комментария

                    trackInfo = new DtoTrackInfo(totalDistance, totalTime, totalElevationGain, firstLatitude, firstLongitude, lastLatitude, lastLongitude, comment);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        // Выводим данные на консоль
        if (trackInfo != null) {
            System.out.println("Общее расстояние: " + trackInfo.getTotalDistance());
            System.out.println("Общее время: " + trackInfo.getTotalTime());
            System.out.println("Общий подъем: " + trackInfo.getTotalElevationGain());
            System.out.println("Первая широта: " + trackInfo.getFirstLatitude());
            System.out.println("Первая долгота: " + trackInfo.getFirstLongitude());
            System.out.println("Последняя широта: " + trackInfo.getLastLatitude());
            System.out.println("Последняя долгота: " + trackInfo.getLastLongitude());
            System.out.println("Комментарий: " + trackInfo.getComment());
        }

        return trackInfo;
    }
}

