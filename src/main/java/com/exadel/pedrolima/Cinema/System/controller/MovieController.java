package com.exadel.pedrolima.Cinema.System.controller;

import com.exadel.pedrolima.Cinema.System.repository.MovieRepository;
import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.entity.Movie;
import com.exadel.pedrolima.entity.Session;
import com.exadel.pedrolima.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final SessionRepository sessionRepository;

    public MovieController(MovieRepository movieRepository,  SessionRepository sessionRepository) {
        this.movieRepository = movieRepository;
        this.sessionRepository = sessionRepository;
    }

    //Get
    @GetMapping
    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    //Get (id)
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id){
        return movieRepository.findById(Long.valueOf(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie){
        return movieRepository.save(movie);
    }

    @PostMapping("/{movieId}/sessions")
    public ResponseEntity<Session> createSession(@PathVariable Long movieId, @RequestBody Session session){
        return movieRepository.findById(movieId)
                .map(movie -> {
                    session.setMovie(movie);
                    return ResponseEntity.ok(sessionRepository.save(session));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie){
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(updatedMovie.getTitle());
                    movie.setGenre(updatedMovie.getGenre());
                    movie.setDuration(updatedMovie.getDuration());
                    return ResponseEntity.ok(movieRepository.save(movie));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        return movieRepository.findById(id)
                .map(movie -> {
                    movieRepository.delete(movie);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
