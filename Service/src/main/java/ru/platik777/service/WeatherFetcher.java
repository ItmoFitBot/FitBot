package ru.platik777.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

// TODO: переделать формат возврата значений под наши сервисы
public class WeatherFetcher {

    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";

    public static void main(String[] args) {
        double latitude = 37.7749;  // Замените на нужную широту
        double longitude = -122.4194;  // Замените на нужную долготу
        LocalDate date = LocalDate.of(2024, 6, 27);  // Замените на нужную дату

        try {
            String weather = getWeatherByCoordinates(latitude, longitude, date);
            System.out.println(weather);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getWeatherByCoordinates(double latitude, double longitude, LocalDate date) throws IOException, InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);

        String url = String.format(Locale.US, "%s?latitude=%.4f&longitude=%.4f&daily=temperature_2m_max,temperature_2m_min,precipitation_sum&start_date=%s&end_date=%s&timezone=UTC",
                BASE_URL, latitude, longitude, formattedDate, formattedDate);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Проверка кода ответа и наличия данных
        if (response.statusCode() == 200) {
            JSONObject jsonResponse = new JSONObject(response.body());
            if (jsonResponse.has("daily")) {
                return parseWeatherResponse(jsonResponse, formattedDate);
            } else {
                return "No daily weather data found in the response.";
            }
        } else {
            return "Failed to fetch weather data: HTTP " + response.statusCode() + "\nResponse body: " + response.body();
        }
    }

    private static String parseWeatherResponse(JSONObject jsonResponse, String date) {
        JSONObject daily = jsonResponse.getJSONObject("daily");

        JSONArray dates = daily.getJSONArray("time");
        JSONArray tempMax = daily.getJSONArray("temperature_2m_max");
        JSONArray tempMin = daily.getJSONArray("temperature_2m_min");
        JSONArray precipitation = daily.getJSONArray("precipitation_sum");

        for (int i = 0; i < dates.length(); i++) {
            if (dates.getString(i).equals(date)) {
                double maxTemp = tempMax.getDouble(i);
                double minTemp = tempMin.getDouble(i);
                double precip = precipitation.getDouble(i);

                return String.format("Weather on %s:\nMax Temperature: %.2f°C\nMin Temperature: %.2f°C\nPrecipitation: %.2fmm",
                        date, maxTemp, minTemp, precip);
            }
        }

        return "No weather data available for the specified date.";
    }
}
