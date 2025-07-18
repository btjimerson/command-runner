package dev.snbv2.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the history of commands executed by the application.
 * Provides methods to add commands to history and retrieve the command history.
 */
public class CommandHistory {

    private List<String> history;

    /**
     * Creates a new CommandHistory instance with an empty history list.
     */
    public CommandHistory() {
        history = new ArrayList<String>();
    }

    /**
     * Returns an unmodifiable view of the command history.
     * Changes to the returned list will not affect the internal history.
     * 
     * @return An unmodifiable view of the command history
     */
    public List<String> getHistory() {
        return Collections.unmodifiableList(history);
    }

    /**
     * Adds a command to the beginning of the history list.
     * 
     * @param command The command to add to history
     */
    public void addHistory(String command) {
        this.history.add(0, command);
    }

}
