package org.FitBot;

import lombok.Getter;

@Getter
public class TrackPoint {

    public TrackPoint(double time, double distance, double speed, double elevationGain, long heartRate, double grade, double lat, double lon) {
        this.time = time;
        this.distance = distance;
        this.speed = speed;
        this.elevationGain = elevationGain;
        this.heartRate = heartRate;
        this.grade = grade;
        this.lat = lat;
        this.lon = lon;
    }

    private final double distance;
    private final double time;
    private final double speed;
    private final double elevationGain;
    private final long heartRate;
    private final double grade;
    private final double lat;
    private final double lon;

}