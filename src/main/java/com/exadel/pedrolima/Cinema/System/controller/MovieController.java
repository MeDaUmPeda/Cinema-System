package com.exadel.pedrolima.Cinema.System.controller;

import com.exadel.pedrolima.Cinema.System.DTO.MovieRequest;
import com.exadel.pedrolima.Cinema.System.DTO.MovieResponse;
import com.exadel.pedrolima.Cinema.System.repository.MovieRepository;
import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.Cinema.System.service.MovieService;
import com.exadel.pedrolima.entity.Movie;
import com.exadel.pedrolima.entity.Session;
import com.exadel.pedrolima.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<MovieResponse> getAllMovies(){
        return movieService.getAllMovies();
    }

    //Get (id)
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@RequestBody MovieRequest request){
        return ResponseEntity.ok(movieService.createMovie(request));
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
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @RequestBody MovieRequest request){
        return movieService.updateMovie(id, request).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        return movieService.deleteMovie(id)
                ?  ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
