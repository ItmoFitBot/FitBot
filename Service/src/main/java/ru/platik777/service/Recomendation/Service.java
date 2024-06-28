package ru.platik777.service.Recomendation;

import org.FitBot.DtoGeneralCharect;
import org.FitBot.DtoTrackInfo;

import java.util.List;

public class Service {

    public DtoGeneralCharect getGeneralСharacteristics(List<DtoTrackInfo> dtoTrackInfos) {
        double genDistance = 0;
        double genTime = 0;
        double genHeight = 0;

        for (DtoTrackInfo dtoTrackInfo : dtoTrackInfos) {
            genDistance += dtoTrackInfo.getTotalDistance();
            genTime += dtoTrackInfo.getTotalTime();
            genHeight += dtoTrackInfo.getTotalElevationGain();
        }

        return new DtoGeneralCharect(genTime, genDistance, genHeight);
    }
}
