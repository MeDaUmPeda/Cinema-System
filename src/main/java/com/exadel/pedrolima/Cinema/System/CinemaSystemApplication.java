package com.exadel.pedrolima.Cinema.System;

import com.exadel.pedrolima.Cinema.System.repository.MovieRepository;
import com.exadel.pedrolima.Cinema.System.repository.SessionRepository;
import com.exadel.pedrolima.Cinema.System.repository.TicketRepository;
import com.exadel.pedrolima.Cinema.System.repository.UserRepository;
import com.exadel.pedrolima.entity.Movie;
import com.exadel.pedrolima.entity.Session;
import com.exadel.pedrolima.entity.User;
import com.exadel.pedrolima.entity.enums.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.exadel.pedrolima.entity.enums.UserRole.CUSTOMER;

@SpringBootApplication
@EntityScan("com.exadel.pedrolima.entity")
public class CinemaSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository, MovieRepository movieRepository, SessionRepository sessionRepository) {
		return args -> {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

//			User u = new User();
//			u.setName("Enrico");
//			u.setEmail("enrico@email.com");
//			u.setRole(CUSTOMER);
//			userRepository.save(u);

//			User n = new User(null, CUSTOMER, "cury@email.com", "Cury");
//			User j = new User(null, CUSTOMER, "jamal@email.com", "Jamal");
//			User g = new User(null, CUSTOMER, "gabriell@email.com", "Gabriell");
//			userRepository.saveAll(List.of(j,g,n));
//
//			Movie batman = new Movie(null, "The Batman",176, "Action");
//			Movie starWars = new Movie(null, " Star Wars: Episode III - Revenge of the Sith", 140, "Sci-fi");
//			Movie grownUps = new Movie (null, "Grown-Ups", 102, "Comedy");
//
//			movieRepository.saveAll(List.of(batman,starWars,grownUps));
//
//			Session batSession = new Session(null, LocalDateTime.parse("19/09/2025 20:30", formatter), 90, batman, new ArrayList<>());
//			Session starWarsSession = new Session(null, LocalDateTime.parse("19/09/2025 19:00", formatter), 90, starWars, new ArrayList<>());
//			Session grownUpsSession = new Session(null, LocalDateTime.parse("19/09/2025 20:00", formatter), 90, grownUps, new ArrayList<>());
//
//			sessionRepository.saveAll(List.of(batSession, starWarsSession, grownUpsSession));
//
//			System.out.println("all saved in database");
		};
	}

}
