package org.FitBot.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TrackPointDTO {
    private Long id;
    private Long trackId;
    private String latitude;
    private String longitude;
    private Date dateTime;
    private Integer height;
}