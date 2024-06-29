package org.FitBot;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Builder
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

}
