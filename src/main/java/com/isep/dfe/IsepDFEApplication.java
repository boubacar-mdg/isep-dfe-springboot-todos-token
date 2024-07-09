package com.isep.dfe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.isep.dfe.users.models.enums.Role;
import com.isep.dfe.users.repositories.UserRepository;

@SpringBootApplication
@EnableAsync
public class IsepDFEApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsepDFEApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			
			if(userRepository.findByUsername("admin").isEmpty()) {
				com.isep.dfe.users.models.entities.User user = com.isep.dfe.users.models.entities.User.builder()
                .firstName("Admin")
                .lastName("Compte")
                .username("admin")
                .password(passwordEncoder.encode("passer"))
                .role(Role.ADMIN)
                .build();

        		userRepository.save(user);
			}

		};
	}

	

}
