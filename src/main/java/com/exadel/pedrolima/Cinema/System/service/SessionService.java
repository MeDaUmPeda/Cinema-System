package com.exadel.pedrolima.Cinema.System.service;

import com.exadel.pedrolima.Cinema.System.DTO.SessionRequest;
import com.exadel.pedrolima.Cinema.System.DTO.SessionResponse;
import com.exadel.pedrolima.Cinema.System.Exception.BadRequestException;
import com.exadel.pedrolima.Cinema.System.Exception.ResourceNotFoundException;
import com.exadel.pedrolima.Cinema.System.repository.MovieRepository;
import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.entity.Movie;
import com.exadel.pedrolima.entity.Session;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;

    public SessionService(SessionRepository sessionRepository, MovieRepository movieRepository) {
        this.sessionRepository = sessionRepository;
        this.movieRepository = movieRepository;
    }

    private SessionResponse convertToDto(Session session){
        return new SessionResponse(
                session.getId(), session.getDateTime(), session.getAvailableSeats(), session.getMovie().getTitle()
        );
    }

    public List<SessionResponse> getAllSessions() {
        return sessionRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public SessionResponse getSessionById(Long id) {
        Session session = sessionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("We can't find session with id: " + id));
        return convertToDto(session);
    }

    public SessionResponse createSession(SessionRequest request) {
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("We can't find movie with id: " + request.getMovieId()));

        if (request.getAvailableSeats() == null || request.getAvailableSeats() <= 0){
            throw new BadRequestException("Available seats must be greater than 0");
        }

        Session session = new Session();
        session.setDateTime(request.getDateTime());
        session.setAvailableSeats(request.getAvailableSeats());
        session.setMovie(movie);

        Session saved = sessionRepository.save(session);
        return convertToDto(saved);

    }

    public SessionResponse updateSession(Long id, SessionRequest request) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("We can't find this session with id: " + id));

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("We can't find movie with id: " + request.getMovieId()));

        if(request.getAvailableSeats() == null || request.getAvailableSeats() <= 0){
            throw new BadRequestException("Available seats must be greater than 0");
        }

        session.setDateTime(request.getDateTime());
        session.setAvailableSeats(request.getAvailableSeats());
        session.setMovie(movie);

        Session updatedSession =  sessionRepository.save(session);
        return convertToDto(updatedSession);
    }

    public void deleteSessionById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("We can't find this session with id: " + id));
                sessionRepository.delete(session);
    }

    public List<SessionResponse> getSessionsByDate(LocalDateTime date) {
        return sessionRepository.findByDateTime(date).stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
