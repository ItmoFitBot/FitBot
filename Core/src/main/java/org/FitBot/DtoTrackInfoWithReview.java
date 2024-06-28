package org.FitBot;

import lombok.Getter;

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

}
