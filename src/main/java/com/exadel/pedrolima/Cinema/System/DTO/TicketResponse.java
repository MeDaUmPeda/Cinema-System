package com.exadel.pedrolima.Cinema.System.DTO;

import com.exadel.pedrolima.entity.Ticket;
import com.exadel.pedrolima.entity.enums.TicketStatus;

public class TicketResponse {

    private Long Id;
    private String seatNumber;
    private TicketStatus status;

    public TicketResponse(){

    }

    public TicketResponse(Long id, String seatNumber, TicketStatus status) {
        Id = id;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public Long getId() {
        return Id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public TicketStatus getStatus() {
        return status;
    }
}
