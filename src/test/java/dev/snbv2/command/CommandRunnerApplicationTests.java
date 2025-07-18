package dev.snbv2.command;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the CommandRunnerApplication class.
 * These tests verify that the Spring Boot application context loads correctly
 * and that required beans are properly configured.
 */
@SpringBootTest
class CommandRunnerApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;
	
	/**
	 * Tests that the Spring application context loads successfully.
	 */
	@Test
	void contextLoads() {
		// Verify that the application context loads successfully
		assertNotNull(applicationContext, "Application context should not be null");
	}
	
	/**
	 * Tests that the RestTemplate bean is properly created and available in the application context.
	 */
	@Test
	void restTemplateBeanShouldExist() {
		// Verify that the RestTemplate bean is created and available in the context
		assertTrue(applicationContext.containsBean("restTemplate"), 
				"RestTemplate bean should be defined in the application context");
		
		RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
		assertNotNull(restTemplate, "RestTemplate bean should not be null");
	}
	
	/**
	 * Tests that the main method can be executed without throwing any exceptions.
	 */
	@Test
	void mainMethodShouldExecuteWithoutException() {
		// Test that the main method can be called without throwing exceptions
		assertDoesNotThrow(() -> CommandRunnerApplication.main(new String[]{}),
				"Main method should execute without throwing exceptions");
	}

}
