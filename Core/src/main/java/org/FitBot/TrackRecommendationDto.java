package org.FitBot;

public class TrackRecommendationDto {

    private final double trackSeverity;

    public TrackRecommendationDto(double trackSeverity) {
        this.trackSeverity = trackSeverity;
    }

    public double getTrackSeverity() {
        return trackSeverity;
    }
}
