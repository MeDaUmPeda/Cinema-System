package com.exadel.pedrolima.Cinema.System.controller;

import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.Cinema.System.repository.TicketRepository;
import com.exadel.pedrolima.Cinema.System.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;

    public UserController(UserRepository userRepository,  TicketRepository ticketRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.sessionRepository = sessionRepository;
    }

    //GET
    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //GET (Id)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //GET (role)
    @GetMapping("/role/{role}")
    public List<User> getuserByRole(@PathVariable UserRole role){
        return userRepository.findByRole(role);
    }

    //POST
    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    //POST
    @PostMapping("/{userId}/reserve/{sessionId}")
    public ResponseEntity<Ticket> reserveTicket(@PathVariable Long userId, @PathVariable Long sessionId, @RequestParam String seatNumber){

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Session> sessionOpt = sessionRepository.findById(sessionId);

        if(userOpt.isEmpty() || sessionOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Session session = sessionOpt.get();
        if(session.getAvailableSeats() <= 0){
            return ResponseEntity.badRequest().build();
        }

        Ticket ticket = new Ticket();
        ticket.setSeatNumber(seatNumber);
        ticket.setStatus(TicketStatus.RESERVED);
        ticket.setUser(userOpt.get());
        ticket.setSession(session);

        session.setAvailableSeats(session.getAvailableSeats() - 1);
        sessionRepository.save(session);

        Ticket savedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(savedTicket);
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser){
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setRole(updatedUser.getRole());
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        return userRepository.findById(id).map(user ->{
            userRepository.delete(user);
            return ResponseEntity.noContent().<Void>build();
        })
                .orElse(ResponseEntity.notFound().build());
    }

}
