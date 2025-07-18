package dev.snbv2.command;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CommandTests {

    @Test
    void getCommandAsArrayShouldHandleNullCommand() {
        Command command = new Command();
        // command.setCommand(null); - command is null by default
        
        // Verify that getCommandAsArray() returns an empty array for null command
        assertArrayEquals(new String[0], command.getCommandAsArray());
    }
    
    @Test
    void getCommandAsArrayShouldSplitCommandString() {
        Command command = new Command();
        command.setCommand("echo test");
        
        // Verify that getCommandAsArray() splits the command string correctly
        assertArrayEquals(new String[]{"echo", "test"}, command.getCommandAsArray());
    }
    
    @Test
    void getCommandAsArrayShouldHandleEmptyString() {
        Command command = new Command();
        command.setCommand("");
        
        // Verify that getCommandAsArray() handles empty strings correctly
        assertArrayEquals(new String[]{""}, command.getCommandAsArray());
    }
}