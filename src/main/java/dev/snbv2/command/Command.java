package dev.snbv2.command;

public class Command {

    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getCommandAsArray() {
        if (command == null) {
            return new String[0];
        }
        return command.split(" ");
    }
}
