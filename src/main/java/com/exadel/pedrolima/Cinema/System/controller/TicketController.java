package com.exadel.pedrolima.Cinema.System.controller;

import com.exadel.pedrolima.Cinema.System.DTO.TicketRequest;
import com.exadel.pedrolima.Cinema.System.DTO.TicketResponse;
import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.Cinema.System.repository.TicketRepository;
import com.exadel.pedrolima.Cinema.System.service.TicketService;
import com.exadel.pedrolima.entity.Session;
import com.exadel.pedrolima.entity.Ticket;
import com.exadel.pedrolima.entity.enums.TicketStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<TicketResponse> getAllTickets(){
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id){
        return ticketService.getTicketById(id)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketRequest request) {
        return ticketService.createTicket(request)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long id, @RequestBody TicketRequest updatedTicket){
       return ticketService.updateTicket(id, updatedTicket)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<TicketResponse> cancelTicket(@PathVariable Long id){
        return ticketService.cancelTicket(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id){
        if(ticketService.deleteTicketById(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
