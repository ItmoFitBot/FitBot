package org.FitBot;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DtoGeneralCharect {

    public DtoGeneralCharect(double time, double distance, double height) {
        this.time = time;
        this.distance = distance;
        this.height = height;
    }

    private double distance;
    private double time;
    private double height;
}
