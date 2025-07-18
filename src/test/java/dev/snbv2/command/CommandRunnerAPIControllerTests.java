package dev.snbv2.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests for the CommandRunnerAPIController class.
 * These tests verify the REST API functionality for command execution.
 */
@WebMvcTest(CommandRunnerAPIController.class)
class CommandRunnerAPIControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private Runtime runtime;

    @Mock
    private Process process;

    /**
     * Setup method that runs before each test.
     */
    @BeforeEach
    void setUp() {
        // Setup code if needed
    }

    /**
     * Tests that the execute endpoint successfully runs a command and returns its output.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void executeShouldRunCommandAndReturnResult() throws Exception {
        // Setup command and expected output
        Command command = new Command();
        command.setCommand("echo test");
        String commandOutput = "Command output\nMultiple lines";
        InputStream inputStream = new ByteArrayInputStream(commandOutput.getBytes());

        // Mock runtime and process
        try (MockedStatic<Runtime> mockedRuntime = Mockito.mockStatic(Runtime.class)) {
            mockedRuntime.when(Runtime::getRuntime).thenReturn(runtime);
            when(runtime.exec(any(String[].class))).thenReturn(process);
            when(process.getInputStream()).thenReturn(inputStream);

            // Execute test
            MvcResult result = mockMvc.perform(post("/api/v1/command")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(command)))
                    .andExpect(status().isOk())
                    .andReturn();

            // Verify response
            String responseContent = result.getResponse().getContentAsString();
            assertEquals(commandOutput + "\n", responseContent);
        }
    }

    /**
     * Tests that the execute endpoint properly handles IOExceptions by returning the error message.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void executeShouldHandleIOException() throws Exception {
        // Setup command
        Command command = new Command();
        command.setCommand("invalid command");

        // Mock runtime to throw IOException
        try (MockedStatic<Runtime> mockedRuntime = Mockito.mockStatic(Runtime.class)) {
            mockedRuntime.when(Runtime::getRuntime).thenReturn(runtime);
            when(runtime.exec(any(String[].class))).thenThrow(new IOException("Command failed"));

            // Execute test
            MvcResult result = mockMvc.perform(post("/api/v1/command")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(command)))
                    .andExpect(status().isOk())
                    .andReturn();

            // Verify error message is returned
            String responseContent = result.getResponse().getContentAsString();
            assertEquals("Command failed", responseContent);
        }
    }

    /**
     * Tests that the execute endpoint can handle empty commands without errors.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void executeShouldHandleEmptyCommand() throws Exception {
        // Setup empty command
        Command command = new Command();
        command.setCommand("");

        // Execute test
        mockMvc.perform(post("/api/v1/command")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk());
    }

    // Test for null command handling moved to CommandTests.java
}