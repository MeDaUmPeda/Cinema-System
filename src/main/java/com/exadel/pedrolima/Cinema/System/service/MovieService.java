package com.exadel.pedrolima.Cinema.System.service;

import com.exadel.pedrolima.Cinema.System.DTO.MovieRequest;
import com.exadel.pedrolima.Cinema.System.DTO.MovieResponse;
import com.exadel.pedrolima.Cinema.System.Exception.BusinessException;
import com.exadel.pedrolima.Cinema.System.Exception.ResourceNotFoundException;
import com.exadel.pedrolima.Cinema.System.repository.MovieRepository;
import com.exadel.pedrolima.entity.Movie;
import com.exadel.pedrolima.entity.Session; 
import org.springframework.stereotype.Service;

import java.util.List;
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

    public MovieResponse getMovieById(Long id){
        return movieRepository.findById(id).map(this::convertToDto).orElseThrow(() -> new ResourceNotFoundException("Movie with id " + id + " not found"));
    }

    public MovieResponse createMovie(MovieRequest request) {
        if(request.getTitle() == null || request.getTitle().isBlank()){
            throw new BusinessException("The title can't be empty");
        }
        if(request.getDuration() == null || request.getDuration() <= 0){
            throw new BusinessException("The duration needs to be grater than zero");
        }
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDuration(request.getDuration());
        movie.setGenre(request.getGenre());

        return convertToDto(movieRepository.save(movie));
    }

    public MovieResponse updateMovie(Long id, MovieRequest request) {
        return movieRepository.findById(id).map(movie -> {
            if(request.getTitle() == null || request.getTitle().isBlank()){
                throw new BusinessException("The title can't be empty");
            }
            if(request.getDuration() == null || request.getDuration() <= 0){
                throw new BusinessException("The duration needs to be grater than zero");
            }

            movie.setTitle(request.getTitle());
            movie.setDuration(request.getDuration());
            movie.setGenre(request.getGenre());
            return convertToDto(movieRepository.save(movie));
        })
                .orElseThrow(() -> new ResourceNotFoundException("Movie with id " + id + " not found"));
    }

    public void deleteMovie(Long id){
        if(!movieRepository.existsById(id)){
            throw new ResourceNotFoundException("Movie with id " + id + " not found");
        }
        movieRepository.deleteById(id);
    }

}
