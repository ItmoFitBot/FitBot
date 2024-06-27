package org.FitBot;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class WeatherDto {
    private Double latitude;
    private Double longitude;
    private Double maxTemperature;
    private LocalDate date;
    private Double minTemperature;
    private Double precipitation;
}
