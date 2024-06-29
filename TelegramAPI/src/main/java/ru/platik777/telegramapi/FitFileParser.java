package ru.platik777.telegramapi;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.FitBot.DtoTrackInfo;

import java.io.FileReader;
import java.io.IOException;

public class FitFileParser {

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
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return trackInfo;
    }
}
