package ru.platik777.service.repositories;

import org.FitBot.entities.TrackMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackMarkRepository extends JpaRepository<TrackMark, Long> {
    Optional<TrackMark> findByTrackId(String trackId);
}
