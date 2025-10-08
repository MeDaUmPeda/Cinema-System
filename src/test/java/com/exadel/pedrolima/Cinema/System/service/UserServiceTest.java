package com.exadel.pedrolima.Cinema.System.service;

import com.exadel.pedrolima.Cinema.System.DTO.CreateUserRequest;
import com.exadel.pedrolima.Cinema.System.DTO.UserResponse;
import com.exadel.pedrolima.Cinema.System.Exception.BadRequestException;
import com.exadel.pedrolima.Cinema.System.Exception.ResourceNotFoundException;
import com.exadel.pedrolima.Cinema.System.repository.UserRepository;
import com.exadel.pedrolima.entity.User;
import com.exadel.pedrolima.entity.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        user = new User(1L, UserRole.ADMIN, "admin@email.com", "Admin");
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("Admin", result.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByIdSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(1L);

        assertEquals("Admin", response.getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void testGetUserByRoleSuccess() {
        when(userRepository.findByRole(UserRole.ADMIN)).thenReturn(List.of(user));

        List<UserResponse> result = userService.getUserByRole(UserRole.ADMIN);

        assertEquals(1, result.size());
        verify(userRepository).findByRole(UserRole.ADMIN);
    }

    @Test
    void testGetUserByRoleEmpty() {
        when(userRepository.findByRole(UserRole.CUSTOMER)).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByRole(UserRole.CUSTOMER));
    }

    @Test
    void testCreateUserSuccess() {
        CreateUserRequest request = new CreateUserRequest("Admin", "admin@email.com", UserRole.ADMIN);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.createUser(request);

        assertEquals("Admin", response.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateUserMissingEmail() {
        CreateUserRequest request = new CreateUserRequest("User", "", UserRole.CUSTOMER);
        assertThrows(BadRequestException.class, () -> userService.createUser(request));
    }

    @Test
    void testUpdateUserSuccess() {
        CreateUserRequest request = new CreateUserRequest("Updated", "new@email.com", UserRole.ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.updateUser(1L, request);

        assertEquals("Updated", response.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUserNotFound() {
        CreateUserRequest request = new CreateUserRequest("User", "user@email.com", UserRole.ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, request));
    }

    @Test
    void testDeleteUserSuccess() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUserById(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUserById(99L));
    }
}
