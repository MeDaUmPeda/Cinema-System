package com.exadel.pedrolima.Cinema.System.service;

import com.exadel.pedrolima.Cinema.System.DTO.TicketRequest;
import com.exadel.pedrolima.Cinema.System.DTO.TicketResponse;
import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.Cinema.System.repository.TicketRepository;
import com.exadel.pedrolima.Cinema.System.repository.UserRepository;
import com.exadel.pedrolima.entity.Session;
import com.exadel.pedrolima.entity.Ticket;
import com.exadel.pedrolima.entity.User;
import com.exadel.pedrolima.entity.enums.TicketStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return ticketRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Optional<TicketResponse> getTicketById(Long id){
        return ticketRepository.findById(id).map(this::convertToDto);
    }

    public Optional<TicketResponse> createTicket(TicketRequest request){
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        Optional<Session> sessionOpt = sessionRepository.findById(request.getSessionId());

        if(userOpt.isEmpty() || sessionOpt.isEmpty()){
            return Optional.empty();
        }

        Session session = sessionOpt.get();
        if(session.getAvailableSeats() <= 0){
            return Optional.empty();
        }

        Ticket ticket = new Ticket();
        ticket.setSeatNumber(request.getSeatNumber());
        ticket.setStatus(request.getStatus());
        ticket.setUser(userOpt.get());
        ticket.setSession(session);

        session.setAvailableSeats(session.getAvailableSeats() - 1);
        sessionRepository.save(session);

        Ticket savedTicket = ticketRepository.save(ticket);
        return Optional.of(convertToDto(savedTicket));
    }



    public boolean deleteTicketById(Long id){
        if(ticketRepository.existsById(id)){
            ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<TicketResponse> updateTicket(Long id, TicketRequest request){
        return ticketRepository.findById(id).map( ticket -> {
            ticket.setSeatNumber(request.getSeatNumber());
            ticket.setStatus(request.getStatus());
            Ticket saved = ticketRepository.save(ticket);
            return convertToDto(saved);
        });
    }

    public Optional<TicketResponse> cancelTicket(Long id){
        return ticketRepository.findById(id).map(ticket -> {
            ticket.setStatus(TicketStatus.CANCELED);

            Session session = ticket.getSession();
            session.setAvailableSeats(session.getAvailableSeats() + 1);
            sessionRepository.save(session);

            Ticket saved = ticketRepository.save(ticket);
            return convertToDto(saved);
        });
    }

}
