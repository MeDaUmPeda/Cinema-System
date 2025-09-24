package com.exadel.pedrolima.Cinema.System.repository;

import com.exadel.pedrolima.entity.Session;
import com.exadel.pedrolima.entity.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Override
    Optional<Session> findById(Long id);

    List<Session> findByDateTime(LocalDateTime dateTime);

}
