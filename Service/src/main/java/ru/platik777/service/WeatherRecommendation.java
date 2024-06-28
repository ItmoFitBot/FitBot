package ru.platik777.service;

import org.FitBot.WeatherRecommendationDto;
import org.FitBot.exceptions.InvalidStatus;
import org.FitBot.exceptions.WeatherAtDateNotFound;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class WeatherRecommendation {
    private final WeatherFetcher fetcher = new WeatherFetcher();

    private final Map<WeatherType, String> equipmentRecommendationPhrase = HashMap.newHashMap(WeatherType.values().length);

    public WeatherRecommendationDto getReccomendation(double latitude, double longitude, LocalDate date) throws WeatherAtDateNotFound, InvalidStatus, IOException, InterruptedException {
        fetcher.getWeatherByCoordinates(latitude, longitude,date);

    }

    private WeatherRecommendationDto analiseWeather() {

    }
}
