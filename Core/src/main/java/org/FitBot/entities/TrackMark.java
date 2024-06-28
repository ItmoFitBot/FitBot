package org.FitBot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "track_mark")
@Getter
@Setter
public class TrackMark {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @OneToOne
    @JoinColumn(name="track_id", nullable=false)
    private Track track;
    private Date date;
    private Integer generalDifficult;
    private Integer physicalLoad;
    private Integer landscapeAttractiveness;
    private Integer safety;
    private Integer generalImpression;
}
