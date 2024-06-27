package org.FitBot;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class WeatherDto {
    private LocalDate date;
    private Double maxTemperature;
    private Double minTemperature;
    private Double precipitation;
    private Double windSpeed;
}
