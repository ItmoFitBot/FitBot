package org.FitBot;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
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
    ArrayList<TrackPoint> trackPoints = new ArrayList<>();
}
