package com.exadel.pedrolima.Cinema.System.controller;

import com.exadel.pedrolima.Cinema.System.DTO.SessionRequest;
import com.exadel.pedrolima.Cinema.System.DTO.SessionResponse;
import com.exadel.pedrolima.Cinema.System.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
   public List<SessionResponse> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.getSessionById(id));
    }

    @GetMapping("/date")
    public ResponseEntity<List<SessionResponse>> getSessionsByDate(@RequestParam("date") LocalDateTime date) {
        return ResponseEntity.ok(sessionService.getSessionsByDate(date));
    }

    @PostMapping
    public ResponseEntity<SessionResponse> createSession(@RequestBody SessionRequest request) {
        SessionResponse createdSession = sessionService.createSession(request);
        return ResponseEntity.created(URI.create("/api/sessions" + createdSession.getId())).body(createdSession);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionResponse> updatedSession(@PathVariable Long id, @RequestBody SessionRequest request) {
        return ResponseEntity.ok(sessionService.updateSession(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSessionById(id);
        return ResponseEntity.noContent().build();
    }
}
