package ru.platik777.service.services;

import org.FitBot.DTO.TrackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.FitBot.entities.Track;
import ru.platik777.service.repositories.TrackRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    public List<TrackDTO> getTracks() {
        return trackRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TrackDTO> getTrackById(Long id) {
        return trackRepository.findById(id)
                .map(this::convertToDTO);
    }

    public TrackDTO saveTrack(TrackDTO trackDto) {
        Track track = convertToEntity(trackDto);
        Track savedTrack = trackRepository.save(track);
        return convertToDTO(savedTrack);
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }

    private TrackDTO convertToDTO(Track track) {
        TrackDTO trackDto = new TrackDTO();
        trackDto.setId(track.getId());
        trackDto.setAverageSpeed(track.getAverageSpeed());
        trackDto.setAverageHeartRate(track.getAverageHeartRate());
        trackDto.setTrackDistance(track.getTrackDistance());
        return trackDto;
    }

    private Track convertToEntity(TrackDTO trackDto) {
        Track track = new Track();
        track.setId(trackDto.getId());
        track.setAverageSpeed(trackDto.getAverageSpeed());
        track.setAverageHeartRate(trackDto.getAverageHeartRate());
        track.setTrackDistance(trackDto.getTrackDistance());
        return track;
    }
}
