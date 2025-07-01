package dev.snbv2.command;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    
    private List<String> history;

    public CommandHistory() {
        history = new ArrayList<String>();
    }

    public List<String> getHistory() {
        return history;
    }

    public void addHistory(String command) {
        this.history.add(0, command);
    }

}
