package com.mohammedsaqibkhan.userservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		// Load .env file
		Dotenv dotenv = Dotenv.configure()
				.directory("/Users/mohammedsaqib/Eatscape/eatscape-backend/userservice/")
				.load();

		// Set system properties for database configuration
		System.setProperty("SERVICE_NAME", dotenv.get("SERVICE_NAME"));
		System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_DRIVER", dotenv.get("DB_DRIVER"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		// Set system properties for email configuration
		System.setProperty("EMAIL_HOST", dotenv.get("EMAIL_HOST"));
		System.setProperty("EMAIL_PORT", dotenv.get("EMAIL_PORT"));
		System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
		System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));
		System.setProperty("EMAIL_PROTOCOL", dotenv.get("EMAIL_PROTOCOL"));
		System.setProperty("EMAIL_SMTP_AUTH", dotenv.get("EMAIL_SMTP_AUTH"));
		System.setProperty("EMAIL_SMTP_STARTTLS_ENABLE", dotenv.get("EMAIL_SMTP_STARTTLS_ENABLE"));
		System.setProperty("EMAIL_SMTP_STARTTLS_REQUIRED", dotenv.get("EMAIL_SMTP_STARTTLS_REQUIRED"));
		System.setProperty("EMAIL_SMTP_CONNECTIONTIMEOUT", dotenv.get("EMAIL_SMTP_CONNECTIONTIMEOUT"));
		System.setProperty("EMAIL_SMTP_TIMEOUT", dotenv.get("EMAIL_SMTP_TIMEOUT"));
		System.setProperty("EMAIL_SMTP_WRITETIMEOUT", dotenv.get("EMAIL_SMTP_WRITETIMEOUT"));

		// Set system properties for logging configuration
		System.setProperty("LOGGING_LEVEL_ROOT", dotenv.get("LOGGING_LEVEL_ROOT"));
		System.setProperty("LOGGING_LEVEL_ORG_SPRINGFRAMEWORK", dotenv.get("LOGGING_LEVEL_ORG_SPRINGFRAMEWORK"));

		// Start the application
		SpringApplication.run(UserserviceApplication.class, args);
	}
}