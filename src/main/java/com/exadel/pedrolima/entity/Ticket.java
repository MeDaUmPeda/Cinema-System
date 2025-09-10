package com.exadel.pedrolima.entity;

import com.exadel.pedrolima.entity.enums.TicketStatus;

import java.util.Objects;

public class Ticket {

    private Long id;
    private String seatNumber;
    private TicketStatus status;

    public Ticket(){

    }

    public Ticket(Long id, String seatNumber, TicketStatus status) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.status = status;
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
                '}';
    }
}
