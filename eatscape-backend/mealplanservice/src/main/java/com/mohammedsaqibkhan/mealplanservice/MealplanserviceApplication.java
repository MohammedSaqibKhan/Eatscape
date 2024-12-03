package com.mohammedsaqibkhan.mealplanservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@EnableFeignClients
public class MealplanserviceApplication {

	public static void main(String[] args) {
		// Load environment variables from .env file
		Dotenv dotenv = Dotenv.configure()
				.directory("/Users/mohammedsaqib/Eatscape/eatscape-backend/mealplanservice/.env")
				.load();

		// Check if the value is loaded
		System.out.println("Recipe Service URL: " + dotenv.get("RECIPE_SERVICE_URL"));
		// Set environment variables to System properties
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));


		SpringApplication.run(MealplanserviceApplication.class, args);
	}
}
