package org.FitBot;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;

@Getter
public class DtoTrackInfo {
    private final double totalDistance;
    private final double totalTime;
    private final double totalElevationGain;
    private final long heartRate;
    private final long countMesg;
    private final long firstLatitude;
    private final long firstLongitude;

    private final long lastLatitude;
    private final long lastLongitude;
    private final Date date;
    ArrayList<TrackPoint> trackPoints = new ArrayList<>();

    public DtoTrackInfo(double totalDistance, double totalTime, double totalElevationGain, long heartRate, long countMesg, long firstLatitude, long firstLongitude, long lastLatitude, long lastLongitude, Date date, ArrayList<TrackPoint> trackPoints) {
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.totalElevationGain = totalElevationGain;
        this.heartRate = heartRate;
        this.countMesg = countMesg;
        this.firstLatitude = firstLatitude;
        this.firstLongitude = firstLongitude;
        this.lastLatitude = lastLatitude;
        this.lastLongitude = lastLongitude;
        this.date = date;
        this.trackPoints = trackPoints;
    }

}
