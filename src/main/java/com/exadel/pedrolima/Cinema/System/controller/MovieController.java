package com.exadel.pedrolima.Cinema.System.controller;

import com.exadel.pedrolima.Cinema.System.DTO.CreateMovieRequest;
import com.exadel.pedrolima.Cinema.System.DTO.MovieResponse;
import com.exadel.pedrolima.Cinema.System.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    //Get
    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    //Get (id)
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id){
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@RequestBody CreateMovieRequest request){
        MovieResponse created = movieService.createMovie(request);
        return ResponseEntity.created(URI.create("api/movies/" + created.getId())).body(created);
    }

//    @PostMapping("/{movieId}/sessions")
//    public ResponseEntity<Session> createSession(@PathVariable Long movieId, @RequestBody Session session){
//        return movieRepository.findById(movieId)
//                .map(movie -> {
//                    session.setMovie(movie);
//                    return ResponseEntity.ok(sessionRepository.save(session));
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @RequestBody CreateMovieRequest request){
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

}
