package dev.snbv2.command;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the CommandHistory class.
 * These tests verify the behavior of the command history tracking functionality.
 */
class CommandHistoryTests {

    private CommandHistory commandHistory;

    /**
     * Setup method that runs before each test.
     * Creates a new CommandHistory instance for testing.
     */
    @BeforeEach
    void setUp() {
        commandHistory = new CommandHistory();
    }

    /**
     * Tests that a newly created CommandHistory has an empty history list.
     */
    @Test
    void newCommandHistoryShouldBeEmpty() {
        // A newly created CommandHistory should have an empty history list
        List<String> history = commandHistory.getHistory();
        
        assertNotNull(history, "History list should not be null");
        assertTrue(history.isEmpty(), "History list should be empty");
    }

    /**
     * Tests that adding commands to history places them at the beginning of the list.
     */
    @Test
    void addHistoryShouldAddCommandToBeginningOfList() {
        // Adding a command should add it to the beginning of the history list
        commandHistory.addHistory("command1");
        
        List<String> history = commandHistory.getHistory();
        assertEquals(1, history.size(), "History should have one entry");
        assertEquals("command1", history.get(0), "First command should be 'command1'");
        
        // Add another command
        commandHistory.addHistory("command2");
        
        // Check that the new command is at the beginning
        history = commandHistory.getHistory();
        assertEquals(2, history.size(), "History should have two entries");
        assertEquals("command2", history.get(0), "First command should be 'command2'");
        assertEquals("command1", history.get(1), "Second command should be 'command1'");
    }
    
    /**
     * Tests that the CommandHistory can handle empty command strings.
     */
    @Test
    void addHistoryShouldHandleEmptyCommands() {
        // Adding an empty command should still work
        commandHistory.addHistory("");
        
        List<String> history = commandHistory.getHistory();
        assertEquals(1, history.size(), "History should have one entry");
        assertEquals("", history.get(0), "First command should be empty string");
    }
    
    /**
     * Tests that the CommandHistory can handle null command strings.
     */
    @Test
    void addHistoryShouldHandleNullCommands() {
        // Adding a null command should still work
        commandHistory.addHistory(null);
        
        List<String> history = commandHistory.getHistory();
        assertEquals(1, history.size(), "History should have one entry");
        assertNull(history.get(0), "First command should be null");
    }
    
    /**
     * Tests that the getHistory method returns an unmodifiable list.
     * Attempts to modify the returned list should throw UnsupportedOperationException.
     */
    @Test
    void getHistoryShouldReturnUnmodifiableList() {
        // The getHistory method returns an unmodifiable view of the internal list
        // Attempts to modify the returned list should throw UnsupportedOperationException
        
        commandHistory.addHistory("command1");
        List<String> history = commandHistory.getHistory();
        
        // Attempt to modify the returned list should throw exception
        assertThrows(UnsupportedOperationException.class, () -> {
            history.add("command2");
        }, "Should throw UnsupportedOperationException when trying to modify the returned list");
        
        // The internal list should remain unchanged
        assertEquals(1, commandHistory.getHistory().size(), "History should still have one entry");
        assertEquals("command1", commandHistory.getHistory().get(0), "First command should still be 'command1'");
    }
}