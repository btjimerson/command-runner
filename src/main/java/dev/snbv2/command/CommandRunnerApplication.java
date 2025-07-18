package dev.snbv2.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Main application class for the Command Runner application.
 * Provides the entry point and configuration for the Spring Boot application.
 */
@SpringBootApplication
public class CommandRunnerApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CommandRunnerApplication.class, args);
	}

	/**
	 * Creates a RestTemplate bean for HTTP client operations.
	 * 
	 * @return A configured RestTemplate instance
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
