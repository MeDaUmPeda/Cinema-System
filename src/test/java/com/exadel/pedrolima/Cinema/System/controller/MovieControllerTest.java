package com.exadel.pedrolima.Cinema.System.controller;

import com.exadel.pedrolima.Cinema.System.DTO.CreateMovieRequest;
import com.exadel.pedrolima.Cinema.System.DTO.MovieResponse;
import com.exadel.pedrolima.Cinema.System.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllMovies() throws Exception {
        when(movieService.getAllMovies())
                .thenReturn(List.of(
                        new MovieResponse(1L, "Matrix", 136, "Sci-fi", Collections.emptyList())
                ));

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Matrix"));
    }


    @Test
    void testGetMovieById() throws Exception {
        when(movieService.getMovieById(1L))
                .thenReturn(
                        new MovieResponse(1L, "Matrix", 136, "Sci-fi", Collections.emptyList())
                );

        mockMvc.perform(get("/api/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Matrix"));
    }

    @Test
    void testCreateMovie() throws Exception {
        CreateMovieRequest request = new CreateMovieRequest("Matrix", 136, "Sci-fi");
        MovieResponse movieResponse = new MovieResponse(1L, "Matrix", 136, "Sci-Fi", Collections.emptyList() );

        when(movieService.createMovie(any(CreateMovieRequest.class))).thenReturn(movieResponse);

        mockMvc.perform(post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Matrix"));
    }

    @Test
    void testDeleteMovie() throws Exception {
        when(movieService.deleteMovie(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/movies/1"))
                .andExpect(status().isNoContent());
    }

}

