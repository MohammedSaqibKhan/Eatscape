package com.mohammedsaqibkhan.nutritionservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NutritionserviceApplication {

	public static void main(String[] args) {
		// Load .env file
		Dotenv dotenv = Dotenv.configure()
				.directory("/Users/mohammedsaqib/Eatscape/eatscape-backend/nutritionservice/.env")
				.load();

		// Set system properties for database configuration
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_DRIVER", dotenv.get("DB_DRIVER"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		// Set system properties for service configuration
		System.setProperty("SERVICE_NAME", dotenv.get("SERVICE_NAME"));
		System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));

		// Set system properties for logging
		System.setProperty("LOGGING_LEVEL_ORG_SPRINGFRAMEWORK", dotenv.get("LOGGING_LEVEL_ORG_SPRINGFRAMEWORK"));
		System.setProperty("LOGGING_LEVEL_CUSTOM_PACKAGE", dotenv.get("LOGGING_LEVEL_CUSTOM_PACKAGE"));
		System.setProperty("LOGGING_LEVEL_FEIGN_CLIENT", dotenv.get("LOGGING_LEVEL_FEIGN_CLIENT"));
		System.setProperty("LOGGING_LEVEL_SPRING_WEB", dotenv.get("LOGGING_LEVEL_SPRING_WEB"));

		// Set system properties for API keys
		System.setProperty("NUTRITIONIX_APP_ID", dotenv.get("NUTRITIONIX_APP_ID"));
		System.setProperty("NUTRITIONIX_APP_KEY", dotenv.get("NUTRITIONIX_APP_KEY"));

		// Start the Spring Boot application
		SpringApplication.run(NutritionserviceApplication.class, args);
	}
}