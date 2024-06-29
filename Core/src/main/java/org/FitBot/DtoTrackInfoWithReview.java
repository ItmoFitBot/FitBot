package org.FitBot;

import lombok.Getter;
import org.FitBot.DTO.TrackDTO;

import java.util.ArrayList;
import java.util.Date;

@Getter
public class DtoTrackInfoWithReview {
    public DtoTrackInfoWithReview(DtoTrackInfo dtoTrackInfo, Integer generaDifficult, Integer physicalDifficult, Integer psychicalDifficult, Integer safety, Integer landscapeAttractive, Integer general) {
        this.dtoTrackInfo = dtoTrackInfo;
        this.generaDifficult = generaDifficult;
        this.physicalDifficult = physicalDifficult;
        this.psychicalDifficult = psychicalDifficult;
        this.safety = safety;
        this.landscapeAttractive = landscapeAttractive;
        this.general = general;
    }

    DtoTrackInfo dtoTrackInfo;
    private final Integer generaDifficult;
    private final Integer physicalDifficult;
    private final Integer psychicalDifficult;
    private final Integer landscapeAttractive;
    private final Integer safety;
    private final Integer general;

    public DtoTrackInfoWithReview(TrackDTO trackDTO, Integer generaDifficult, Integer physicalDifficult, Integer psychicalDifficult, Integer safety, Integer landscapeAttractive, Integer general) {
        this.generaDifficult = generaDifficult;
        this.physicalDifficult = physicalDifficult;
        this.psychicalDifficult = psychicalDifficult;
        this.safety = safety;
        this.landscapeAttractive = landscapeAttractive;
        this.general = general;
        this.dtoTrackInfo = new DtoTrackInfo(trackDTO.getTrackDistance(), (double) trackDTO.getTrackDistance() / trackDTO.getAverageSpeed(), 0, trackDTO.getAverageHeartRate(), 0, 0, 0, 0, 0, new Date(), new ArrayList<>());
    }

}
