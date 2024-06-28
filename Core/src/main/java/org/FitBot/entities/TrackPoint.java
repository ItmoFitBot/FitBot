package org.FitBot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "trackpoints")
@Getter
@Setter
public class TrackPoint {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="trackId", nullable=false)
    private Track track;
    private String latitude;
    private String longitude;
    private Date dateTime;
    private Integer height;
}