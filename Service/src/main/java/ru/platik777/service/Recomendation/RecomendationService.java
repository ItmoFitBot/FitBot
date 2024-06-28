package ru.platik777.service.Recomendation;

import org.FitBot.DtoTrackInfo;
import org.FitBot.DtoTrackInfoWithReview;
import org.FitBot.WeatherRecommendationDto;
import org.FitBot.exceptions.InvalidStatus;
import org.FitBot.exceptions.WeatherAtDateNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

@Component
public class RecomendationService {

    private TrackRecommendation trackRecommendation;
    private WeatherRecommendation weatherRecommendation;

    @Autowired
    public RecomendationService(TrackRecommendation trackRecommendation, WeatherRecommendation weatherRecommendation) {
        this.trackRecommendation = trackRecommendation;
        this.weatherRecommendation = weatherRecommendation;
    }

    public static LocalDate convertToLocalDateViaInstant(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public String getRecommendation(ArrayList<DtoTrackInfoWithReview> trackInfo, DtoTrackInfo dtoTrackInfo) {
        String resWeather;
        try {
            WeatherRecommendationDto weatherDto = weatherRecommendation.getReccomendation(dtoTrackInfo.getFirstLatitude(), dtoTrackInfo.getFirstLongitude(), convertToLocalDateViaInstant(dtoTrackInfo.getDate()));
            resWeather = weatherDto.getWeatherAlert();

        } catch (InterruptedException | WeatherAtDateNotFound | IOException | InvalidStatus e) {
            resWeather = "Undefined weather recommendation";
        }

        double rec = trackRecommendation.getRecommendationInfo(trackInfo, dtoTrackInfo);
        if (rec < 5.0) {
            return "Вам будет очень сложно на этом пути, поэтому перед походом следует потренироваться";
        }
        else if (rec < 7.0) {
            return "Могут возникнуть сложности, но не стоит их бояться";
        }
        return "Вам этот маршрут по плечу" + resWeather;
    }

}
