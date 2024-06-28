package ru.platik777.service.Recomendation;

import org.FitBot.DtoGeneralCharect;
import org.FitBot.DtoTrackInfo;
import org.FitBot.ReadPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private ReadPort readPort;

    @Autowired
    public Service(ReadPort readPort) {
        this.readPort = readPort;
    }

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

    public void  addFitInfo(String pathToFitFile) {
        readPort.read(pathToFitFile);
        //взаимодействие с Data Access слоем
    }
}
