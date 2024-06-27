package org.FitBot;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeatherRecommendationDto {
    private List<String> equipmentRecommendations;
    private String weatherAlert;
    private Double weatherNicestCoefficient;
}
