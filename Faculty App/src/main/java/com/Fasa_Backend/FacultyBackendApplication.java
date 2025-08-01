package com.Fasa_Backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FassaBackendApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("APP_USERNAME", dotenv.get("APP_USERNAME"));
		System.setProperty("APP_PASSWORD", dotenv.get("APP_PASSWORD"));

		SpringApplication.run(FassaBackendApplication.class, args);
	}
}
