package com.exadel.pedrolima.Cinema.System.controller;

import com.exadel.pedrolima.Cinema.System.DTO.CreateSessionRequest;
import com.exadel.pedrolima.Cinema.System.DTO.SessionResponse;
import com.exadel.pedrolima.Cinema.System.service.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllSessionsShouldReturnList() throws Exception {
        SessionResponse response = new SessionResponse(1L, LocalDateTime.now(), 50, "Matrix");
        when(sessionService.getAllSessions()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/sessions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieTitle").value("Matrix"));
    }

    @Test
    void getSessionByIdShouldReturnSession() throws Exception {
        SessionResponse response = new SessionResponse(1L, LocalDateTime.now(), 50, "Matrix");
        when(sessionService.getSessionById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/sessions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieTitle").value("Matrix"));
    }

    @Test
    void createSessionShouldReturnCreated() throws Exception{
        CreateSessionRequest request = new CreateSessionRequest(LocalDateTime.now(), 40, 1L);
        SessionResponse response = new SessionResponse(1L, request.getDateTime(), 40, "Matrix");

        when(sessionService.createSession(any(CreateSessionRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/sessions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.movieTitle").value("Matrix"));
    }

    @Test
    void updateSessionShouldReturnOk() throws Exception{
        CreateSessionRequest request = new CreateSessionRequest(LocalDateTime.now(), 40, 1L);
        SessionResponse response = new SessionResponse(1L, request.getDateTime(), 50, "Matrix");

        when(sessionService.updateSession(any(Long.class), any(CreateSessionRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/sessions/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableSeats").value(40));
    }

    @Test
    void deleteSessionShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/sessions/1"))
                .andExpect(status().isNoContent());
    }
}
