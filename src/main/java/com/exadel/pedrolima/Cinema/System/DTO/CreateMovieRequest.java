package com.exadel.pedrolima.Cinema.System.DTO;

public class CreateMovieRequest {

    private String title;
    private Integer duration;
    private String genre;

    public CreateMovieRequest(){

    }

    public CreateMovieRequest(String title, Integer duration, String genre) {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
