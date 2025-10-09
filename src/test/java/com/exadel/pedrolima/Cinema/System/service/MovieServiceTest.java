package com.exadel.pedrolima.Cinema.System.service;

import com.exadel.pedrolima.Cinema.System.DTO.CreateMovieRequest;
import com.exadel.pedrolima.Cinema.System.DTO.MovieResponse;
import com.exadel.pedrolima.Cinema.System.repository.MovieRepository;
import com.exadel.pedrolima.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMovies(){
        Movie movie = new Movie(1L, "Matrix",136 , "Sci-fi");
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie));

        List<MovieResponse> movies = movieService.getAllMovies();

        assertEquals(1, movies.size());
        assertEquals("Matrix", movies.get(0).getTitle());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void testGetMoviesById(){
        Movie movie = new Movie(1L, "Matrix",136 , "Sci-fi");
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Optional<MovieResponse> response = Optional.ofNullable(movieService.getMovieById(1L));

        assertTrue(response.isPresent());
        assertEquals("Matrix", response.get().getTitle());
    }

    @Test
    void testCreateMovie(){
        CreateMovieRequest request = new CreateMovieRequest("Matrix",136 , "Sci-fi");
        Movie movie = new Movie(1L, "Matrix",136 , "Sci-fi");

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        MovieResponse response = movieService.createMovie(request);

        assertNotNull(response);
        assertEquals("Matrix", response.getTitle());
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testDeleteMovieSuccess(){
        when(movieRepository.existsById(1L)).thenReturn(true);
        doNothing().when(movieRepository).deleteById(1L);

        boolean result = movieService.deleteMovie(1L);

        assertTrue(result);
        verify(movieRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMovieNotFound(){
        when(movieRepository.existsById(1L)).thenReturn(false);

        boolean result = movieService.deleteMovie(1L);

        assertFalse(result);
        verify(movieRepository, times(1)).deleteById(1L);
    }

}
