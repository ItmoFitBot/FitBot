package ru.platik777.service.services;

import org.FitBot.DTO.TrackMarkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.FitBot.entities.TrackMark;
import ru.platik777.service.repositories.TrackMarkRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrackMarkService {
    @Autowired
    private  TrackMarkRepository trackMarkRepository;

    public TrackMark getTrackMarkByTrackId(int id) {
        return trackMarkRepository.findByTrackId(String.valueOf((long) id)).orElse(null);
    }
    public List<TrackMarkDTO> getTrackMarks() {
        return trackMarkRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TrackMarkDTO> getTrackMarkById(Long id) {
        return trackMarkRepository.findById(id)
                .map(this::convertToDTO);
    }

    public TrackMarkDTO saveTrackMark(TrackMarkDTO trackMarkDto) {
        TrackMark trackMark = convertToEntity(trackMarkDto);
        TrackMark savedTrackMark = trackMarkRepository.save(trackMark);
        return convertToDTO(savedTrackMark);
    }

    public void deleteTrackMark(Long id) {
        trackMarkRepository.deleteById(id);
    }

    private TrackMarkDTO convertToDTO(TrackMark trackMark) {
        TrackMarkDTO trackMarkDto = new TrackMarkDTO();
        //trackMarkDto.setId(trackMark.getId());
        trackMarkDto.setUserId(trackMark.getUser().getId());
        trackMarkDto.setTrackId(trackMark.getTrack().getId());
        trackMarkDto.setDate(trackMark.getDate());
        trackMarkDto.setGeneralDifficult(trackMark.getGeneralDifficult());
        trackMarkDto.setPhysicalLoad(trackMark.getPhysicalLoad());
        trackMarkDto.setLandscapeAttractiveness(trackMark.getLandscapeAttractiveness());
        trackMarkDto.setSafety(trackMark.getSafety());
        trackMarkDto.setGeneralImpression(trackMark.getGeneralImpression());
        return trackMarkDto;
    }

    private TrackMark convertToEntity(TrackMarkDTO trackMarkDto) {
        TrackMark trackMark = new TrackMark();
        //trackMark.setId(trackMarkDto.getId());
        trackMark.setDate(trackMarkDto.getDate());
        trackMark.setGeneralDifficult(trackMarkDto.getGeneralDifficult());
        trackMark.setPhysicalLoad(trackMarkDto.getPhysicalLoad());
        trackMark.setLandscapeAttractiveness(trackMarkDto.getLandscapeAttractiveness());
        trackMark.setSafety(trackMarkDto.getSafety());
        trackMark.setGeneralImpression(trackMarkDto.getGeneralImpression());
        return trackMark;
    }
}

