package com.exadel.pedrolima.Cinema.System.service;

import com.exadel.pedrolima.Cinema.System.DTO.CreateSessionRequest;
import com.exadel.pedrolima.Cinema.System.DTO.SessionResponse;
import com.exadel.pedrolima.Cinema.System.Exception.BadRequestException;
import com.exadel.pedrolima.Cinema.System.Exception.ResourceNotFoundException;
import com.exadel.pedrolima.Cinema.System.repository.MovieRepository;
import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.entity.Movie;
import com.exadel.pedrolima.entity.Session;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private SessionService sessionService;

    private Movie movie;
    private Session session;
    private LocalDateTime now;

    @Test
    void getAllSessionsShouldReturnList(){
        when(sessionRepository.findAll()).thenReturn(List.of(session));

        List<SessionResponse> result = sessionService.getAllSessions();

        assertEquals(1, result.size());
        assertEquals("Matrix", result.get(0).getMovieTitle());
    }

    @Test
    void getSessionsByIdShouldReturnSession(){
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        SessionResponse result = sessionService.getSessionById(1L);

        assertEquals("Matrix", result.getMovieTitle());
    }

    @Test
    void getSessionByIdShouldThrowWhenNotFound(){
        when(sessionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sessionService.getSessionById(99L));
    }

    @Test
    void createSessionShouldCreateSuccessfully(){
        CreateSessionRequest request = new CreateSessionRequest(now, 30, 1L);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        SessionResponse result = sessionService.createSession(request);

        assertEquals("Matrix", result.getMovieTitle());
    }

    @Test
    void createSessionShouldThrowIfSeatsInvalid(){
        CreateSessionRequest request = new CreateSessionRequest(now, 0, 1L);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        assertThrows(BadRequestException.class, () -> sessionService.createSession(request));
    }

    @Test
    void UpdateSessionShouldUpdateSuccessfully(){
        CreateSessionRequest request = new CreateSessionRequest(now.plusDays(1), 40, 1L);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        SessionResponse result = sessionService.updateSession(1L, request);

        assertEquals(50, session.getAvailableSeats());
        assertNotNull(result);
    }

    @Test
    void updateSessionShouldThrowWhenSessionNotFound(){
        CreateSessionRequest request = new CreateSessionRequest(now, 50, 1L);

        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sessionService.updateSession(1L, request));
    }

    @Test
    void deleteSessionShouldWorkSuccessfully(){
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        sessionService.deleteSessionById(1L);

        verify(sessionRepository, times(1)).delete(session);
    }

    @Test
    void deleteSessionShouldThrowWhenSessionNotFound(){
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sessionService.deleteSessionById(1L));
    }

}