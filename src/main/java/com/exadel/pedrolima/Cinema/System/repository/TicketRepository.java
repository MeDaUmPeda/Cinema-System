package com.exadel.pedrolima.Cinema.System.repository;

import com.exadel.pedrolima.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Override
    Optional<Ticket> findById(Long id);

    Optional<Ticket> findBySeatNumber(String seatNumber);
}
