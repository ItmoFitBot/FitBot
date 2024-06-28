package ru.platik777.service;

import jdk.jshell.spi.ExecutionControl;
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

    @lombok.SneakyThrows
    public WeatherRecommendationDto getReccomendation(double latitude, double longitude, LocalDate date) throws WeatherAtDateNotFound, InvalidStatus, IOException, InterruptedException {
        List<WeatherType> weatherTypes = analiseWeather(fetcher.getWeatherByCoordinates(latitude, longitude,date));
        return WeatherRecommendationDto
                .builder()
                .equipmentRecommendations(weatherTypes.stream().map(this::getEquipmentRecommendation).toList())
                .weatherAlert(weatherTypes.stream().map(this::alertMapping).toString())
                .weatherNicestCoefficient(getNicestCoefficient(weatherTypes))
                .build();
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

    private String getEquipmentRecommendation(WeatherType weatherType) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Need to implement equipment recommendation for weather");

    }
    private String alertMapping(WeatherType weatherType) {
        if ((weatherType == WeatherType.STORM || weatherType == WeatherType.BIG_TEMPERATURE_DIFFERENCE || weatherType == WeatherType.WINDY)) {
            return "Лучше остаться дома. Погодные условия опасны для жизни";
        }
        return "";
    }

    private Double getNicestCoefficient(List<WeatherType> weatherType) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Need to implement value for weather");
    }
}
