package com.exadel.pedrolima.Cinema.System;

import com.exadel.pedrolima.Cinema.System.repository.UserRepository;
import com.exadel.pedrolima.entity.User;
import com.exadel.pedrolima.entity.enums.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import static com.exadel.pedrolima.entity.enums.UserRole.CUSTOMER;

@SpringBootApplication
@EntityScan("com.exadel.pedrolima.entity")
public class CinemaSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository) {
		return args -> {
			User u = new User();
			u.setName("Enrico");
			u.setEmail("enrico@email.com");
			u.setRole(CUSTOMER);
			userRepository.save(u);

			System.out.println("User is saved in database");
		};
	}

}
