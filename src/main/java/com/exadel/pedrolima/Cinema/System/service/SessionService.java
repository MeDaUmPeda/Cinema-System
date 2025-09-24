package com.exadel.pedrolima.Cinema.System.service;

import com.exadel.pedrolima.Cinema.System.DTO.SessionRequest;
import com.exadel.pedrolima.Cinema.System.DTO.SessionResponse;
import com.exadel.pedrolima.Cinema.System.repository.MovieRepository;
import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
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

    public Optional<SessionResponse> getSessionById(Long id) {
        return sessionRepository.findById(id).map(this::convertToDto);
    }

    public Optional<SessionResponse> createSession(SessionRequest request) {
        return movieRepository.findById(request.getMovieId()).map(movie -> {
            Session session = new Session();
            session.setDateTime(request.getDateTime());
            session.setAvailableSeats(request.getAvaibleSeats());
            session.setMovie(movie);

            Session saved = sessionRepository.save(session);
            return convertToDto(saved);
        });
    }

    public Optional<SessionResponse> updateSession(Long id, SessionRequest request) {
        return sessionRepository.findById(id).flatMap(session -> movieRepository.findById(request.getMovieId())
                .map(movie -> {
                    session.setDateTime(request.getDateTime());
                    session.setAvailableSeats(request.getAvaibleSeats());
                    session.setMovie(movie);

                    Session updated = sessionRepository.save(session);
                    return convertToDto(updated);
                }));
    }

    public boolean deleteSessionById(Long id) {
        return sessionRepository.findById(id).map(session -> {
            sessionRepository.delete(session);
            return true;
        }).orElse(false);
    }

    public List<SessionResponse> getSessionsByDate(LocalDateTime date) {
        return sessionRepository.findByDateTime(date).stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
