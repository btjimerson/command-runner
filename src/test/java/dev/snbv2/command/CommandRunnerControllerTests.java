package dev.snbv2.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import jakarta.servlet.http.HttpSession;

/**
 * Tests for the CommandRunnerController class.
 * These tests verify the web UI functionality for command execution.
 */
@WebMvcTest(CommandRunnerController.class)
class CommandRunnerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Runtime runtime;

    @Mock
    private Process process;

    private MockHttpSession session;

    /**
     * Setup method that runs before each test.
     * Initializes a mock HTTP session for testing.
     */
    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    /**
     * Tests that the index endpoint adds a command to the model and initializes command history.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void indexShouldAddCommandToModelAndInitializeHistory() throws Exception {
        MvcResult result = mockMvc.perform(get("/index").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("command"))
                .andReturn();

        // Verify command history was initialized in session
        HttpSession resultSession = result.getRequest().getSession();
        assertNotNull(resultSession.getAttribute("commandHistory"));
        assertTrue(resultSession.getAttribute("commandHistory") instanceof CommandHistory);
    }

    /**
     * Tests that the root path redirects to the index page correctly.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void indexShouldWorkWithRootPath() throws Exception {
        mockMvc.perform(get("/").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("command"));
    }

    /**
     * Tests that the execute endpoint successfully runs a command, returns its output,
     * and adds the command to history.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void executeShouldRunCommandAndReturnResult() throws Exception {
        // Setup command output
        String commandOutput = "Command output\nMultiple lines";
        InputStream inputStream = new ByteArrayInputStream(commandOutput.getBytes());

        // Mock runtime and process
        try (MockedStatic<Runtime> mockedRuntime = Mockito.mockStatic(Runtime.class)) {
            mockedRuntime.when(Runtime::getRuntime).thenReturn(runtime);
            when(runtime.exec(any(String[].class))).thenReturn(process);
            when(process.getInputStream()).thenReturn(inputStream);

            // Execute test
            mockMvc.perform(post("/execute").session(session)
                    .param("command", "echo test"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"))
                    .andExpect(model().attributeExists("result"))
                    .andExpect(model().attribute("result", commandOutput + "\n"));

            // Verify command was added to history
            CommandHistory history = (CommandHistory) session.getAttribute("commandHistory");
            assertNotNull(history);
            assertEquals(1, history.getHistory().size());
            assertEquals("echo test", history.getHistory().get(0));
        }
    }

    /**
     * Tests that the execute endpoint properly handles IOExceptions by adding an error attribute to the model.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void executeShouldHandleIOException() throws Exception {
        // Mock runtime to throw IOException
        try (MockedStatic<Runtime> mockedRuntime = Mockito.mockStatic(Runtime.class)) {
            mockedRuntime.when(Runtime::getRuntime).thenReturn(runtime);
            when(runtime.exec(any(String[].class))).thenThrow(new IOException("Command failed"));

            // Execute test
            mockMvc.perform(post("/execute").session(session)
                    .param("command", "invalid command"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"))
                    .andExpect(model().attributeExists("error"))
                    .andExpect(model().attribute("error", "Command failed"));
        }
    }

    /**
     * Tests that the execute endpoint initializes command history if it's not already present in the session.
     * 
     * @throws Exception if the test fails
     */
    @Test
    void executeShouldInitializeHistoryIfNotPresent() throws Exception {
        // Setup command output
        String commandOutput = "Test output";
        InputStream inputStream = new ByteArrayInputStream(commandOutput.getBytes());

        // Mock runtime and process
        try (MockedStatic<Runtime> mockedRuntime = Mockito.mockStatic(Runtime.class)) {
            mockedRuntime.when(Runtime::getRuntime).thenReturn(runtime);
            when(runtime.exec(any(String[].class))).thenReturn(process);
            when(process.getInputStream()).thenReturn(inputStream);

            // Create a new session without history
            MockHttpSession newSession = new MockHttpSession();
            
            // Execute test
            mockMvc.perform(post("/execute").session(newSession)
                    .param("command", "echo test"))
                    .andExpect(status().isOk());

            // Verify history was initialized
            CommandHistory history = (CommandHistory) newSession.getAttribute("commandHistory");
            assertNotNull(history);
            assertEquals(1, history.getHistory().size());
        }
    }
}