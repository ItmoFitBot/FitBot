package org.FitBot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TrackPoint {
    private final double distance;
    private final double time;
    private final double speed;
    private final double elevationGain;
    private final long heartRate;
    private final double grade;
}