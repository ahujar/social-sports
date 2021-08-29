package com.example.social.sports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.social.sports.data.**")
@EntityScan("com.example.social.sports.model.**")
@ComponentScan("com.example.social.sports.**")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
