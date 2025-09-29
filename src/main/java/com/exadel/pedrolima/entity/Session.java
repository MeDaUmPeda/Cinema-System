package com.exadel.pedrolima.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Integer availableSeats;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @OneToMany(mappedBy = "session",  cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public Session(){

    }

    public Session(Long id, LocalDateTime dateTime, Integer availableSeats, Movie movie, List<Ticket> tickets) {
        this.id = id;
        this.dateTime = dateTime;
        this.availableSeats = availableSeats;
        this.movie = movie;
        this.tickets = tickets;
    }

    public Long getId() {
        return id;
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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(dateTime, session.dateTime) && Objects.equals(availableSeats, session.availableSeats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, availableSeats);
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", availableSeats=" + availableSeats +
                ", movie=" + movie +
                ", tickets=" + tickets +
                '}';
    }
}
