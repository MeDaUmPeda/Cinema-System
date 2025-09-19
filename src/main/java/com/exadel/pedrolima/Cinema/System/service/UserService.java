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
    private final TicketRepository ticketRepository;
    private final SessionRepository  sessionRepository;

    public UserService(UserRepository userRepository,  TicketRepository ticketRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.sessionRepository = sessionRepository;
    }

    private UserResponse convertToDto(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    private TicketResponse convertToDto(Ticket ticket){
        return new TicketResponse(ticket.getId(), ticket.getSeatNumber(), ticket.getStatus());
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

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<TicketResponse> reserveTicket(Long userId, Long sessionId, TicketRequest ticketRequest){
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Session> sessionOpt = sessionRepository.findById(sessionId);

        if(userOpt.isEmpty() || sessionOpt.isEmpty()){
            return Optional.empty();
        }

        Session session = sessionOpt.get();
        if(session.getAvailableSeats() <= 0){
            return Optional.empty();
        }

        Ticket ticket = new Ticket();
        ticket.setSeatNumber(ticketRequest.getSeatNumber());
        ticket.setStatus(TicketStatus.RESERVED);
        ticket.setUser(userOpt.get());
        ticket.setSession(session);

        session.setAvailableSeats(session.getAvailableSeats() - 1);
        sessionRepository.save(session);

        Ticket savedTicket = ticketRepository.save(ticket);
        return Optional.of(convertToDto(savedTicket));

    }
}
