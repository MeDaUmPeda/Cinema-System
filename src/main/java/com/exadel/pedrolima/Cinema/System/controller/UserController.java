package com.exadel.pedrolima.Cinema.System.controller;

import com.exadel.pedrolima.Cinema.System.DTO.CreateUserRequest;
import com.exadel.pedrolima.Cinema.System.DTO.UserResponse;
import com.exadel.pedrolima.Cinema.System.service.UserService;
import com.exadel.pedrolima.entity.enums.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //GET
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //GET (Id)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //GET (role)
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getuserByRole(@PathVariable UserRole role){
        return ResponseEntity.ok(userService.getUserByRole(role));
    }

    //POST
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request){
        UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.created(URI.create("/api/users" + createdUser.getId())).body(createdUser);
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody CreateUserRequest request){
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

//    //POST
//    @PostMapping("/{userId}/reserve/{sessionId}")
//    public ResponseEntity<TicketResponse> reserveTicket(@PathVariable Long userId, @PathVariable Long sessionId, @RequestParam TicketRequest ticketRequest){
//
//        return userService.reserveTicket(userId, sessionId, ticketRequest)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.badRequest().build());
//    }

}