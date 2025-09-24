package com.exadel.pedrolima.Cinema.System.DTO;

import java.time.LocalDateTime;

public class SessionRequest {

    private LocalDateTime dateTime;
    private Integer avaibleSeats;
    private Long movieId;

    public SessionRequest(){

    }

    public SessionRequest(LocalDateTime dateTime, Integer avaibleSeats, Long movieId) {
        this.dateTime = dateTime;
        this.avaibleSeats = avaibleSeats;
        this.movieId = movieId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getAvaibleSeats() {
        return avaibleSeats;
    }

    public void setAvaibleSeats(Integer avaibleSeats) {
        this.avaibleSeats = avaibleSeats;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
