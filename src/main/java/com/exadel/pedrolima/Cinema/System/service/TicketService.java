package com.exadel.pedrolima.Cinema.System.service;

import com.exadel.pedrolima.Cinema.System.DTO.CreateTicketRequest;
import com.exadel.pedrolima.Cinema.System.DTO.TicketResponse;
import com.exadel.pedrolima.Cinema.System.Exception.ResourceNotFoundException;
import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.Cinema.System.repository.TicketRepository;
import com.exadel.pedrolima.Cinema.System.repository.UserRepository;
import com.exadel.pedrolima.entity.Session;
import com.exadel.pedrolima.entity.Ticket;
import com.exadel.pedrolima.entity.User;
import com.exadel.pedrolima.entity.enums.TicketStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository, SessionRepository sessionRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    private TicketResponse convertToDto(Ticket ticket){
        return new TicketResponse(ticket.getId(), ticket.getSeatNumber(), ticket.getStatus());
    }

    public List<TicketResponse> getAllTickets(){
        return ticketRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TicketResponse getTicketById(Long id){
        return ticketRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Ticket with id: " + id + " not found")
                );
    }

    public TicketResponse createTicket(CreateTicketRequest request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        Session session = sessionRepository.findById(request.getSessionId()).
                orElseThrow(
                        () -> new ResourceNotFoundException("Session not found")
                );

        if(session.getAvailableSeats() <= 0){
            throw new ResourceNotFoundException("We don't have enough available seats in this session");
        }

        Ticket ticket = new Ticket();
        ticket.setSeatNumber(request.getSeatNumber());
        ticket.setStatus(request.getStatus() != null ? request.getStatus() : TicketStatus.RESERVED);
        ticket.setUser(user);
        ticket.setSession(session);

        session.setAvailableSeats(session.getAvailableSeats() - 1);
        sessionRepository.save(session);

        Ticket savedTicket = ticketRepository.save(ticket);
        return convertToDto(savedTicket);
    }

    public void deleteTicketById(Long id){
        if(ticketRepository.existsById(id)){
            throw new ResourceNotFoundException("Ticket with id: " + id + " not found");
        }
        ticketRepository.deleteById(id);
    }

    public TicketResponse updateTicket(Long id, CreateTicketRequest request){
        return ticketRepository.findById(id).map( ticket -> {
            ticket.setSeatNumber(request.getSeatNumber());
            ticket.setStatus(request.getStatus());
            Ticket saved = ticketRepository.save(ticket);
            return convertToDto(saved);
        }).orElseThrow(() -> new ResourceNotFoundException("Ticket with id: " + id + " not found"));
    }

    public TicketResponse cancelTicket(Long id){
        return ticketRepository.findById(id)
                .map(ticket -> {
            ticket.setStatus(TicketStatus.CANCELED);

            Session session = ticket.getSession();
            session.setAvailableSeats(session.getAvailableSeats() + 1);
            sessionRepository.save(session);

            Ticket saved = ticketRepository.save(ticket);
            return convertToDto(saved);
        })
                .orElseThrow(
                        () -> new ResourceNotFoundException("Ticket with id: " + id + " not found")
                );
    }

}
