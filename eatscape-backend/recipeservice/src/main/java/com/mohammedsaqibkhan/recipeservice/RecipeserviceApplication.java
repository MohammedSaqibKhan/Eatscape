package com.mohammedsaqibkhan.recipeservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipeserviceApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().directory("/Users/mohammedsaqib/Eatscape/eatscape-backend/recipeservice/.env").load();
		// Set system properties for Spring to resolve placeholders
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		SpringApplication.run(RecipeserviceApplication.class, args);
	}

}
