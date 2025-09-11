package com.exadel.pedrolima.Cinema.System.repository;

import com.exadel.pedrolima.entity.Movie;
import com.exadel.pedrolima.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Override
    Optional<Movie> findById(Long id);

    Optional<Movie> findByTitle(String title);

    List<Movie> findAllByGenre(String genre);

    List<Movie> findByDurationGreaterThan(Integer duration);

}
