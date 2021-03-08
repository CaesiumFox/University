package io.github.caesiumfox.lab05.exceptions;

public class EmptyDatabaseException extends Exception {
    public EmptyDatabaseException() {
        super("The database is empty");
    }
}
