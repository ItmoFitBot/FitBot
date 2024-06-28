package org.FitBot.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackDTO {
    private Long id;
    private Integer averageSpeed;
    private Integer averageHeartRate;
    private Integer trackDistance;
}
