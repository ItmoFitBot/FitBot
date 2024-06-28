package org.FitBot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usertrackroute")
@Getter
@Setter
public class UserTrackRoute {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="userId", nullable=false)
    private User user;
    @OneToOne
    @JoinColumn(name="trackId", nullable=false)
    private Track track;
}