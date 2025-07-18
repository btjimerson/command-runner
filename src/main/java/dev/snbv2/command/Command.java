package dev.snbv2.command;

/**
 * Represents a command to be executed by the application.
 * Provides methods to get and set the command string and to convert it to an array.
 */
public class Command {

    private String command;

    /**
     * Gets the command string.
     * 
     * @return The command string
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets the command string.
     * 
     * @param command The command string to set
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Converts the command string to an array by splitting on spaces.
     * If the command is null, returns an empty array.
     * 
     * @return The command as a string array
     */
    public String[] getCommandAsArray() {
        if (command == null) {
            return new String[0];
        }
        return command.split(" ");
    }
}
