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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private TicketService ticketService;

    private User user;
    private Session session;
    private Ticket ticket;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        user = new User(1L, null, "user@email.com", "User");
        session = new Session();
        session.setId(1L);
        session.setAvailableSeats(10);

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setSeatNumber("A1");
        ticket.setStatus(TicketStatus.RESERVED);
        ticket.setUser(user);
        ticket.setSession(session);
    }

    @Test
    void testGetAllTickets() {
        when(ticketRepository.findAll()).thenReturn(List.of(ticket));

        List<TicketResponse> result = ticketService.getAllTickets();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSeatNumber()).isEqualTo("A1");
    }

    @Test
    void testGetTicketByIdSuccess(){
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        TicketResponse response = ticketService.getTicketById(1L);

        assertThat(response.getSeatNumber()).isEqualTo("A1");
    }

    @Test
    void testGetTicketByIdNotFound(){
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->ticketService.getTicketById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Ticket not found");
    }

    @Test
    void testCreateTicketSuccess(){
        CreateTicketRequest request = new CreateTicketRequest("A1",1L, TicketStatus.RESERVED, 1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketResponse response = ticketService.createTicket(request);

        assertThat(response.getSeatNumber()).isEqualTo("A1");
        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    @Test
    void testCreateTicketWhenNoSeatAvailable(){
        session.setAvailableSeats(0);
        CreateTicketRequest request = new CreateTicketRequest("A1",1L, TicketStatus.RESERVED, 1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        assertThatThrownBy(()->ticketService.createTicket(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No seats available");
    }

    @Test
    void testDeteTicketByIdSuccess() {
        when(ticketRepository.existsById(1L)).thenReturn(true);

        ticketService.deleteTicketById(1L);

        verify(ticketRepository).deleteById(1L);
    }

    @Test
    void testDeteTicketByIdNotFound() {
        when(ticketRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(()->ticketService.deleteTicketById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testUpdateTicketSuccess(){
        CreateTicketRequest request = new CreateTicketRequest("B2",1L, TicketStatus.CANCELED, 1L);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketResponse response = ticketService.updateTicket(1L, request);

        assertThat(response.getSeatNumber()).isEqualTo("B2");
        assertThat(response.getStatus()).isEqualTo(TicketStatus.CANCELED);
    }

    @Test
    void testCancelTicketSuccess(){
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketResponse response = ticketService.cancelTicket(1L);

        assertThat(response.getStatus()).isEqualTo(TicketStatus.CANCELED);
        verify(sessionRepository).save(any(Session.class));
    }
}