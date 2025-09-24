package com.exadel.pedrolima.Cinema.System.service;

import com.exadel.pedrolima.Cinema.System.DTO.TicketRequest;
import com.exadel.pedrolima.Cinema.System.DTO.TicketResponse;
import com.exadel.pedrolima.Cinema.System.DTO.UserRequest;
import com.exadel.pedrolima.Cinema.System.DTO.UserResponse;
import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.Cinema.System.repository.TicketRepository;
import com.exadel.pedrolima.Cinema.System.repository.UserRepository;
import com.exadel.pedrolima.entity.Session;
import com.exadel.pedrolima.entity.Ticket;
import com.exadel.pedrolima.entity.User;
import com.exadel.pedrolima.entity.enums.TicketStatus;
import com.exadel.pedrolima.entity.enums.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    private UserResponse convertToDto(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }


    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDto).toList();
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToDto);
    }

    public List<UserResponse> getUserByRole(UserRole role) {
        return userRepository.findByRole(role).stream().map(this::convertToDto).toList();
    }

    public UserResponse createUser(UserRequest request) {
        User user = new User(null, request.getUserRole(), request.getEmail(), request.getName());
        return convertToDto(userRepository.save(user));
    }

    public Optional<UserResponse> updateUser(Long id, UserRequest request) {
        return userRepository.findById(id).map(user -> {
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setRole(request.getUserRole());
            return convertToDto(userRepository.save(user));
        });
    }

    public boolean deleteUserById(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
