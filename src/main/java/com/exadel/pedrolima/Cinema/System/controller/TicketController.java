package com.exadel.pedrolima.Cinema.System.controller;

import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.Cinema.System.repository.TicketRepository;
import com.exadel.pedrolima.entity.Session;
import com.exadel.pedrolima.entity.Ticket;
import com.exadel.pedrolima.entity.enums.TicketStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;

    public TicketController(TicketRepository ticketRepository, SessionRepository sessionRepository) {
        this.ticketRepository = ticketRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id){
        return ticketRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket){
        return ticketRepository.save(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updatedTicket(@PathVariable Long id, @RequestBody Ticket updatedTicket){
       return ticketRepository.findById(id)
               .map(ticket -> {
                   ticket.setSeatNumber(updatedTicket.getSeatNumber());
                   ticket.setStatus(updatedTicket.getStatus());
                   return ResponseEntity.ok(ticketRepository.save(ticket));
               })
               .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Ticket> cancelTicket(@PathVariable Long id){
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticket.setStatus(TicketStatus.CANCELED);

                    Session session = ticket.getSession();
                    session.setAvailableSeats(session.getAvailableSeats() + 1);
                    sessionRepository.save(session);

                    return ResponseEntity.ok(ticketRepository.save(ticket));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id){
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticketRepository.delete(ticket);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
