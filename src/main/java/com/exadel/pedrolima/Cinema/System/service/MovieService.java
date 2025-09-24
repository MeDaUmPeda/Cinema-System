package com.exadel.pedrolima.Cinema.System.service;

import com.exadel.pedrolima.Cinema.System.DTO.MovieRequest;
import com.exadel.pedrolima.Cinema.System.DTO.MovieResponse;
import com.exadel.pedrolima.Cinema.System.repository.MovieRepository;
import com.exadel.pedrolima.entity.Movie;
import com.exadel.pedrolima.entity.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    private MovieResponse convertToDto(Movie movie) {
        List<Long> sessionIds = movie.getSessions().stream().map(Session::getId).collect(Collectors.toList());

        return new MovieResponse(
                movie.getId(), movie.getTitle(), movie.getDuration(), movie.getGenre(), sessionIds
        );
    }

    public List<MovieResponse> getAllMovies(){
        return movieRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Optional<MovieResponse> getMovieById(Long id){
        return movieRepository.findById(id).map(this::convertToDto);
    }

    public MovieResponse createMovie(MovieRequest request) {
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDuration(request.getDuration());
        movie.setGenre(request.getGenre());

        Movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie);
    }

    public Optional<MovieResponse> updateMovie(Long id, MovieRequest request) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(request.getTitle());
            movie.setDuration(request.getDuration());
            movie.setGenre(request.getGenre());
            return convertToDto(movieRepository.save(movie));
        });
    }

    public boolean deleteMovie(Long id){
        if(movieRepository.existsById(id)){
            movieRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
