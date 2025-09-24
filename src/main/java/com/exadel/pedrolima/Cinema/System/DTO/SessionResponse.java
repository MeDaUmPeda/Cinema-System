package com.exadel.pedrolima.Cinema.System.DTO;

import java.time.LocalDateTime;

public class SessionResponse {

    private Long id;
    private LocalDateTime dateTime;
    private Integer availableSeats;
    private String movieTitle;

    public SessionResponse(Long id, LocalDateTime dateTime, Integer availableSeats, String movieTitle) {
        this.id = id;
        this.dateTime = dateTime;
        this.availableSeats = availableSeats;
        this.movieTitle = movieTitle;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}
