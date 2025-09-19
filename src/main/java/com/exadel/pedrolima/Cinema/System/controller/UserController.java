package com.exadel.pedrolima.Cinema.System.controller;

import com.exadel.pedrolima.Cinema.System.DTO.TicketRequest;
import com.exadel.pedrolima.Cinema.System.DTO.TicketResponse;
import com.exadel.pedrolima.Cinema.System.DTO.UserRequest;
import com.exadel.pedrolima.Cinema.System.DTO.UserResponse;
import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.Cinema.System.repository.TicketRepository;
import com.exadel.pedrolima.Cinema.System.repository.UserRepository;
import com.exadel.pedrolima.Cinema.System.service.UserService;
import com.exadel.pedrolima.entity.Session;
import com.exadel.pedrolima.entity.Ticket;
import com.exadel.pedrolima.entity.User;
import com.exadel.pedrolima.entity.enums.TicketStatus;
import com.exadel.pedrolima.entity.enums.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //GET
    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    //GET (Id)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //GET (role)
    @GetMapping("/role/{role}")
    public List<UserResponse> getuserByRole(@PathVariable UserRole role){
        return userService.getUserByRole(role);
    }

    //POST
    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request){
        return userService.createUser(request);
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request){
        return userService.updateUser(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    //POST
    @PostMapping("/{userId}/reserve/{sessionId}")
    public ResponseEntity<TicketResponse> reserveTicket(@PathVariable Long userId, @PathVariable Long sessionId, @RequestParam TicketRequest ticketRequest){

       return userService.reserveTicket(userId, sessionId, ticketRequest)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.badRequest().build());
    }

}
