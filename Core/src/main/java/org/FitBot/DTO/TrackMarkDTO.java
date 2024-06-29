package org.FitBot.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TrackMarkDTO {
    //private Long id;
    private Long userId;
    private Long trackId;
    private Date date;
    private Integer generalDifficult;
    private Integer physicalLoad;
    private Integer landscapeAttractiveness;
    private Integer safety;
    private Integer generalImpression;
}
