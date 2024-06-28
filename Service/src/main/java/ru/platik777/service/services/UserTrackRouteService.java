package ru.platik777.service.services;

import org.FitBot.DTO.UserTrackRouteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.FitBot.entities.UserTrackRoute;
import ru.platik777.service.repositories.UserTrackRouteRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserTrackRouteService {

    private final UserTrackRouteRepository userTrackRouteRepository;

    @Autowired
    public UserTrackRouteService(UserTrackRouteRepository userTrackRouteRepository) {
        this.userTrackRouteRepository = userTrackRouteRepository;
    }

    public List<UserTrackRouteDTO> getUserTrackRoutes() {
        return userTrackRouteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserTrackRouteDTO> getUserTrackRouteById(Long id) {
        return userTrackRouteRepository.findById(id)
                .map(this::convertToDTO);
    }

    public UserTrackRouteDTO saveUserTrackRoute(UserTrackRouteDTO userTrackRouteDto) {
        UserTrackRoute userTrackRoute = convertToEntity(userTrackRouteDto);
        UserTrackRoute savedUserTrackRoute = userTrackRouteRepository.save(userTrackRoute);
        return convertToDTO(savedUserTrackRoute);
    }

    public void deleteUserTrackRoute(Long id) {
        userTrackRouteRepository.deleteById(id);
    }

    private UserTrackRouteDTO convertToDTO(UserTrackRoute userTrackRoute) {
        UserTrackRouteDTO userTrackRouteDto = new UserTrackRouteDTO();
        userTrackRouteDto.setId(userTrackRoute.getId());
        userTrackRouteDto.setUserId(userTrackRoute.getUser().getId());
        userTrackRouteDto.setTrackId(userTrackRoute.getTrack().getId());
        return userTrackRouteDto;
    }

    private UserTrackRoute convertToEntity(UserTrackRouteDTO userTrackRouteDto) {
        UserTrackRoute userTrackRoute = new UserTrackRoute();
        userTrackRoute.setId(userTrackRouteDto.getId());
        // Note: You'll need to fetch or create a User and Track entity for the following lines
        // userTrackRoute.setUser(user);
        // userTrackRoute.setTrack(track);
        return userTrackRoute;
    }
}
