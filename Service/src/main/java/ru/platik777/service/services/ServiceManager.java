package ru.platik777.service.services;

import lombok.Getter;
import org.FitBot.*;
import org.FitBot.DTO.*;
import org.FitBot.entities.Track;
import org.FitBot.entities.TrackMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.platik777.service.Recomendation.RecomendationService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class ServiceManager {

    @Autowired
    public ServiceManager(TrackPointService trackPointService, TrackService trackService, UserService userService, TrackMarkService trackMarkService, RecomendationService recomendationService, UserTrackRouteService userTrackRouteService, ReadPort readerPort) {
        this.trackPointService = trackPointService;
        this.trackService = trackService;
        this.userService = userService;
        this.trackMarkService = trackMarkService;
        this.recomendationService = recomendationService;
        this.userTrackRouteService = userTrackRouteService;
        this.readerPort = readerPort;
    }

    private TrackPointService trackPointService;
    private TrackService trackService;
    private UserService userService;
    private TrackMarkService trackMarkService;
    private UserTrackRouteService userTrackRouteService;
    private RecomendationService recomendationService;
    private ReadPort readerPort;

    private int getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Principal) {
            return userService.getIdByName(((Principal) authentication.getPrincipal()).getName());
        }
        throw new IllegalStateException("No authenticated user found");
    }

    private void saveTrack(DtoTrackInfo dtoTrackInfo){
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setTrackDistance((int) dtoTrackInfo.getTotalDistance());
        trackDTO.setAverageSpeed((int) ((int) dtoTrackInfo.getTotalDistance() / dtoTrackInfo.getTotalTime()));
        trackDTO.setAverageHeartRate((int)dtoTrackInfo.getHeartRate());
        trackService.saveTrack(trackDTO);
    }

    private void saveTrackPointInfo(DtoTrackInfo dtoTrackInfo){
        for (TrackPoint trackPoint : dtoTrackInfo.getTrackPoints()) {
            TrackPointDTO trackPointDTO = new TrackPointDTO();
            trackPointDTO.setDateTime(dtoTrackInfo.getDate());
            trackPointDTO.setLatitude(String.valueOf(trackPoint.getLat()));
            trackPointDTO.setHeight(trackPointDTO.getHeight());
            trackPointDTO.setLongitude(String.valueOf(trackPoint.getLon()));
            trackPointService.saveTrackPoint(trackPointDTO);
        }
    }

    private void saveUserTrackRoute(DtoTrackInfo dtoTrackInfo){
        UserTrackRouteDTO userTrackRouteDTO = new UserTrackRouteDTO();
        userTrackRouteDTO.setUserId((long) getUserId());
        Track track = trackService.findByAverageHeartRateAndAndAverageSpeedAndTrackDistance((int)dtoTrackInfo.getTotalDistance(),(int) (dtoTrackInfo.getTotalDistance() / dtoTrackInfo.getTotalTime()), (int)dtoTrackInfo.getHeartRate());
        if (track != null) {
            userTrackRouteDTO.setTrackId(track.getId());
        }
    }

    public void saveTrackInfo(String fitFilePath) {
        DtoTrackInfo dtoTrackInfo = readerPort.read(fitFilePath);
        saveTrack(dtoTrackInfo);
        saveTrackPointInfo(dtoTrackInfo);
        saveUserTrackRoute(dtoTrackInfo);
    }

    public void saveTrackMark(TrackMarkDTO trackMarkDTO) {
        trackMarkService.saveTrackMark(trackMarkDTO);
    }

    public void saveUser(UserDTO userDTO) {
        userService.saveUser(userDTO);
    }

    public DtoGeneralCharect getGeneralСharacteristics() {
        List<TrackDTO> tracks = trackService.getTracks();
        double genDistance = 0;
        double genTime = 0;
        double avHeartRate = 0;

        int n = 0;
        for (TrackDTO dtoTrackInfo : tracks) {
            n++;
            genDistance += dtoTrackInfo.getTrackDistance();
            genTime += (double) dtoTrackInfo.getTrackDistance() / dtoTrackInfo.getAverageSpeed();
            avHeartRate += dtoTrackInfo.getAverageHeartRate();
        }
        avHeartRate /= n;

        return new DtoGeneralCharect(genTime, genDistance, avHeartRate);
    }

    public String getRecommendation(String fitFilePath){
        DtoTrackInfo dtoTrackInfo = readerPort.read(fitFilePath);
        List<TrackDTO> tracks = trackService.getTracks();
        ArrayList<DtoTrackInfoWithReview> tracksReviewed = new ArrayList<>();
        for (TrackDTO trackDTO : tracks) {
            TrackMark trackMark = trackMarkService.getTrackMarkByTrackId(Math.toIntExact(trackDTO.getId()));
            DtoTrackInfoWithReview dtoTrackInfoWithReview = new DtoTrackInfoWithReview(trackDTO, trackMark.getGeneralDifficult(), trackMark.getPhysicalLoad(), 0, trackMark.getLandscapeAttractiveness(), trackMark.getSafety(), trackMark.getGeneralImpression());
            tracksReviewed.add(dtoTrackInfoWithReview);
        }
        return recomendationService.getRecommendation(tracksReviewed, dtoTrackInfo);
    }

    public List<TrackDTO> history(){
        return trackService.getTracks();
    }

}
