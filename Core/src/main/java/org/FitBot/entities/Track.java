package org.FitBot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tracks")
@Getter
@Setter
public class Track {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Integer averageSpeed;
    private Integer averageHeartRate;
    private Integer trackDistance;
}