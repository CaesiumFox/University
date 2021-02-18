package io.github.caesiumfox.lab05.exceptions;

public class InvalidCommandException extends Exception {
    public InvalidCommandException(String name) {
        super("Invalid command: " + name);
    }
}
