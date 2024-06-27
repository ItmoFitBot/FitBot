package ru.platik777.service;

import org.FitBot.WeatherDto;
import org.FitBot.WeatherRecommendationDto;
import org.FitBot.exceptions.InvalidStatus;
import org.FitBot.exceptions.WeatherAtDateNotFound;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WeatherRecommendation {
    private final WeatherFetcher fetcher = new WeatherFetcher();

    private final Map<WeatherType, String> equipmentRecommendationPhrase = HashMap.newHashMap(WeatherType.values().length);

    public WeatherRecommendationDto getReccomendation(double latitude, double longitude, LocalDate date) throws WeatherAtDateNotFound, InvalidStatus, IOException, InterruptedException {
        fetcher.getWeatherByCoordinates(latitude, longitude,date);
    }

    private List<WeatherType> analiseWeather(WeatherDto weatherDto) {
        List<WeatherType> weatherTypes = new LinkedList<>();
        if (weatherDto.getMaxTemperature() - weatherDto.getMinTemperature() > 25) {
            weatherTypes.add(WeatherType.BIG_TEMPERATURE_DIFFERENCE);
        }
        if (weatherDto.getMinTemperature() < 0) {
            weatherTypes.add(WeatherType.FREEZING);
        }
        if (weatherDto.getPrecipitation() > 200 && weatherDto.getWindSpeed() > 30) {
            weatherTypes.add(WeatherType.STORM);
        }
        if (weatherDto.getMaxTemperature() > 35) {
            weatherTypes.add(WeatherType.HEAT);
        }
        if (weatherDto.getPrecipitation() > 70) {
            weatherTypes.add(WeatherType.RAINY);
        }
        if (weatherDto.getWindSpeed() > 30) {
            weatherTypes.add(WeatherType.WINDY);
        }
        return weatherTypes;
    }
}
