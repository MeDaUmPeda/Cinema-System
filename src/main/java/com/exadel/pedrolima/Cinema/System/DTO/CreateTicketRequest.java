package com.exadel.pedrolima.Cinema.System.DTO;

import com.exadel.pedrolima.entity.enums.TicketStatus;

public class CreateTicketRequest {

    private String seatNumber;
    private Long userId;
    private TicketStatus status;
    private Long sessionId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
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
}
