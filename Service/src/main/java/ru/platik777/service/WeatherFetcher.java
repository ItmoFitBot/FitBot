package ru.platik777.service;

import org.FitBot.WeatherDto;
import org.FitBot.exceptions.InvalidStatus;
import org.FitBot.exceptions.WeatherAtDateNotFound;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class WeatherFetcher {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");;
    private final String BASE_URL = "https://api.open-meteo.com/v1/forecast";

    public WeatherDto getWeatherByCoordinates(double latitude, double longitude, LocalDate date) throws IOException, InterruptedException, WeatherAtDateNotFound, InvalidStatus {
        String formattedDate = date.format(formatter);

        String url = String.format(Locale.US, "%s?latitude=%.4f&longitude=%.4f&daily=temperature_2m_max,temperature_2m_min,precipitation_sum&start_date=%s&end_date=%s&timezone=UTC",
                BASE_URL, latitude, longitude, formattedDate, formattedDate);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        client.close();
        if (response.statusCode() == 200) {
            JSONObject jsonResponse = new JSONObject(response.body());
            if (jsonResponse.has("daily")) {
                return parseWeatherResponse(jsonResponse, date);
            } else {
                throw new WeatherAtDateNotFound();
            }
        } else {
            throw new InvalidStatus();
        }
    }

    private WeatherDto parseWeatherResponse(JSONObject jsonResponse, LocalDate date) {
        JSONObject daily = jsonResponse.getJSONObject("daily");

        JSONArray dates = daily.getJSONArray("time");
        JSONArray tempMax = daily.getJSONArray("temperature_2m_max");
        JSONArray tempMin = daily.getJSONArray("temperature_2m_min");
        JSONArray precipitation = daily.getJSONArray("precipitation_sum");

        for (int i = 0; i < dates.length(); i++) {
            if (dates.getString(i).equals(date.format(formatter))) {
                double maxTemp = tempMax.getDouble(i);
                double minTemp = tempMin.getDouble(i);
                double precip = precipitation.getDouble(i);
                return WeatherDto
                        .builder()
                        .maxTemperature(maxTemp)
                        .minTemperature(minTemp)
                        .precipitation(precip)
                        .date(date)
                        .build();
            }
        }
        return WeatherDto.builder().build();
    }
}
