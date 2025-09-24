package com.exadel.pedrolima.Cinema.System.DTO;

import java.util.List;

public class MovieResponse {

    private Long id;
    private String title;
    private Integer duration;
    private String genre;
    private List<Long> sessionsIds;

    public MovieResponse(Long id, String title, Integer duration, String genre, List<Long> sessionsIds) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.sessionsIds = sessionsIds;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public List<Long> getSessionsIds() {
        return sessionsIds;
    }
}
