package ru.platik777.service.services;

import org.FitBot.DTO.TrackPointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.FitBot.entities.TrackPoint;
import ru.platik777.service.repositories.TrackPointRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrackPointService {

    @Autowired
    private TrackPointRepository trackPointRepository;

    public List<TrackPointDTO> getTrackPoints() {
        return trackPointRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TrackPointDTO> getTrackPointById(Long id) {
        return trackPointRepository.findById(id)
                .map(this::convertToDTO);
    }

    public TrackPointDTO saveTrackPoint(TrackPointDTO trackPointDto) {
        TrackPoint trackPoint = convertToEntity(trackPointDto);
        TrackPoint savedTrackPoint = trackPointRepository.save(trackPoint);
        return convertToDTO(savedTrackPoint);
    }

    public void deleteTrackPoint(Long id) {
        trackPointRepository.deleteById(id);
    }

    private TrackPointDTO convertToDTO(TrackPoint trackPoint) {
        TrackPointDTO trackPointDto = new TrackPointDTO();
        trackPointDto.setId(trackPoint.getId());
        trackPointDto.setTrackId(trackPoint.getTrack().getId());
        trackPointDto.setLatitude(trackPoint.getLatitude());
        trackPointDto.setLongitude(trackPoint.getLongitude());
        trackPointDto.setDateTime(trackPoint.getDateTime());
        trackPointDto.setHeight(trackPoint.getHeight());
        return trackPointDto;
    }

    private TrackPoint convertToEntity(TrackPointDTO trackPointDto) {
        TrackPoint trackPoint = new TrackPoint();
        trackPoint.setId(trackPointDto.getId());
        trackPoint.setLatitude(trackPointDto.getLatitude());
        trackPoint.setLongitude(trackPointDto.getLongitude());
        trackPoint.setDateTime(trackPointDto.getDateTime());
        trackPoint.setHeight(trackPointDto.getHeight());
        return trackPoint;
    }
}

