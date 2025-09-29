package com.exadel.pedrolima.Cinema.System.service;

import com.exadel.pedrolima.Cinema.System.DTO.UserRequest;
import com.exadel.pedrolima.Cinema.System.DTO.UserResponse;
import com.exadel.pedrolima.Cinema.System.Exception.BadRequestException;
import com.exadel.pedrolima.Cinema.System.Exception.ResourceNotFoundException;
import com.exadel.pedrolima.Cinema.System.repository.UserRepository;
import com.exadel.pedrolima.entity.User;
import com.exadel.pedrolima.entity.enums.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("We can't find an user with id: " + id));
    }

    public List<UserResponse> getUserByRole(UserRole role) {
        List<User> users = userRepository.findByRole(role);
        if(users.isEmpty()){
            throw new ResourceNotFoundException("We can't find any user with role: " + role);
        }
        return users.stream().map(this::convertToDto).toList();
    }

    public UserResponse createUser(UserRequest request) {
        if(request.getEmail() == null || request.getEmail().isBlank()){
            throw new BadRequestException("Email is required");
        }
        if(request.getName() == null || request.getName().isBlank()){
            throw new BadRequestException("Name is required");
        }
        if (request.getUserRole() == null){
            throw new BadRequestException("The role of the user is required");
        }
        User user = new User(null, request.getUserRole(), request.getEmail(), request.getName());
        return convertToDto(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("We can't find user with id: " + id));
        if(request.getEmail() == null || request.getEmail().isBlank()){
            throw new BadRequestException("Email is required");
        }
        if(request.getName() == null || request.getName().isBlank()){
            throw new BadRequestException("Name is required");
        }
        if (request.getUserRole() == null){
            throw new BadRequestException("The role of the user is required");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getUserRole());

        return  convertToDto(userRepository.save(user));
    }

    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("We can't find an user with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
