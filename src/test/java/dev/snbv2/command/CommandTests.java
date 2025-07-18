package dev.snbv2.command;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Command class.
 * These tests verify the behavior of the command string handling functionality.
 */
class CommandTests {

    /**
     * Tests that getCommandAsArray handles null command strings correctly.
     * Should return an empty array when the command is null.
     */
    @Test
    void getCommandAsArrayShouldHandleNullCommand() {
        Command command = new Command();
        // command.setCommand(null); - command is null by default
        
        // Verify that getCommandAsArray() returns an empty array for null command
        assertArrayEquals(new String[0], command.getCommandAsArray());
    }
    
    /**
     * Tests that getCommandAsArray correctly splits command strings.
     * Should split the string on spaces into an array of words.
     */
    @Test
    void getCommandAsArrayShouldSplitCommandString() {
        Command command = new Command();
        command.setCommand("echo test");
        
        // Verify that getCommandAsArray() splits the command string correctly
        assertArrayEquals(new String[]{"echo", "test"}, command.getCommandAsArray());
    }
    
    /**
     * Tests that getCommandAsArray handles empty command strings correctly.
     * Should return an array with a single empty string.
     */
    @Test
    void getCommandAsArrayShouldHandleEmptyString() {
        Command command = new Command();
        command.setCommand("");
        
        // Verify that getCommandAsArray() handles empty strings correctly
        assertArrayEquals(new String[]{""}, command.getCommandAsArray());
    }
}