package ru.platik777.service.repositories;

import org.FitBot.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, Long> {
    Optional<Track> findByAverageHeartRateAndAndAverageSpeedAndTrackDistance(int averageHeartRate, int averageSpeed, int trackDistance);
}
