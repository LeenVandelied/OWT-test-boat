package com.owt.boat_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Boat Management System.
 * This Spring Boot application provides a REST API for managing boats.
 * 
 * The application follows a Hexagonal Architecture pattern with:
 * - Domain layer: Core business logic and models
 * - Application layer: Use cases and services
 * - Infrastructure layer: External dependencies and adapters
 */
@SpringBootApplication
public class BoatTestApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 * 
	 * @param args Command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(BoatTestApplication.class, args);
	}

}
