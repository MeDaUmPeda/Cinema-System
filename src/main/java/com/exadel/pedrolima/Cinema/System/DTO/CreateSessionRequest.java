package com.exadel.pedrolima.Cinema.System.DTO;

import java.time.LocalDateTime;

public class SessionRequest {

    private LocalDateTime dateTime;
    private Integer availableSeats;
    private Long movieId;

    public SessionRequest(){

    }

    public SessionRequest(LocalDateTime dateTime, Integer availableSeats, Long movieId) {
        this.dateTime = dateTime;
        this.availableSeats = availableSeats;
        this.movieId = movieId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
