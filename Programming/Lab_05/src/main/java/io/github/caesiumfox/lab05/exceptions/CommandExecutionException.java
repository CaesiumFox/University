package io.github.caesiumfox.lab05.exceptions;

public class CommandExecutionException extends Exception {
    public CommandExecutionException(Exception child) {
        super("Execution exception: " + child.getMessage());
    }
}
