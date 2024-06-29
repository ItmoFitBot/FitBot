package ru.platik777.service.services;

import jakarta.persistence.EntityNotFoundException;
import org.FitBot.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.FitBot.entities.User;
import ru.platik777.service.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO);
    }

    public int getIdByName(String name) throws EntityNotFoundException {
        if (userRepository.findByUsername(name).isPresent())
            return Math.toIntExact(userRepository.findByUsername(name).get().getId());
        throw new EntityNotFoundException("User with name " + name + " not found");
    }

    public UserDTO saveUser(UserDTO userDto) {
        User user = convertToEntity(userDto);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDto = new UserDTO();
        //userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;
    }

    private User convertToEntity(UserDTO userDto) {
        User user = new User();
        //user.setId(userDto.getId());
        user.setName(userDto.getName());
        return user;
    }
}

