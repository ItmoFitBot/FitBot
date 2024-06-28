package org.FitBot;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Builder
public class WeatherRecommendationDto {
    private List<String> equipmentRecommendations;
    private String weatherAlert;
    private Double weatherNicestCoefficient;
}
