package org.FitBot;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
public class DtoTrackInfo {

    private double totalDistance = 0;
    private double totalTime = 0;
    private double totalElevationGain = 0;
    private long heartRate = 0;
    private long countMesg = 0;
    private long firstLatitude = 0;
    private long firstLongitude = 0;

    private long lastLatitude = 0;
    private long lastLongitude = 0;
    private Date date = new Date();
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
