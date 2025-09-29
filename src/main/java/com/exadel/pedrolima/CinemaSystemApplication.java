package com.exadel.pedrolima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaSystemApplication.class, args);
		System.out.println("Everything is OK!");
	}

//	@Bean
//	CommandLineRunner runner(UserRepository userRepository) {
//		return args -> {
//			User u = new User();
//			u.setName("Enrico");
//			u.setEmail("enrico@email.com");
//			u.setRole(CUSTOMER);
//			userRepository.save(u);
//
//			System.out.println("User is saved in database");
//		};
//	}

}
