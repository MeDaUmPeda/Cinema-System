package com.exadel.pedrolima.Cinema.System.DTO;

public class TicketRequest {

    private String seatNumber;

    public TicketRequest() {}

    public TicketRequest(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
