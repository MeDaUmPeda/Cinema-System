package com.exadel.pedrolima.entity;

import com.exadel.pedrolima.entity.enums.TicketStatus;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Ticket(){

    }

    public Ticket(Long id, String seatNumber, TicketStatus status, Session session, User user) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.status = status;
        this.session = session;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(seatNumber, ticket.seatNumber) && status == ticket.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seatNumber, status);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", seatNumber='" + seatNumber + '\'' +
                ", status=" + status +
                ", session=" + session +
                ", user=" + user +
                '}';
    }
}
