package com.exadel.pedrolima.entity;

import java.util.Date;
import java.util.Objects;

public class Session {

    private Long id;
    private Date dateTime;
    private Integer availableSeats;

    public Session(){

    }

    public Session(Long id, Date dateTime, Integer availableSeats) {
        this.id = id;
        this.dateTime = dateTime;
        this.availableSeats = availableSeats;
    }

    public Long getId() {
        return id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
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
                '}';
    }
}
