package org.FitBot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@AllArgsConstructor
public class DtoTrackInfo {
    private double totalDistance;
    private double totalTime;
    private double totalElevationGain;
    private long firstLatitude;
    private long firstLongitude;
    private long lastLatitude;
    private long lastLongitude;
    private String comment;

}
