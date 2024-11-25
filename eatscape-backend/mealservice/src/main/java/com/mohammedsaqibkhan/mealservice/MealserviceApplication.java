package com.mohammedsaqibkhan.mealservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MealserviceApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.directory("/Users/mohammedsaqib/Eatscape/eatscape-backend/mealservice/.env")
				.load();

		// Set environment variables to System properties
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(MealserviceApplication.class, args);
	}

}
