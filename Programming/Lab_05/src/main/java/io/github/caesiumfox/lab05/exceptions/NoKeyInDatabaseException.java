package io.github.caesiumfox.lab05.exceptions;

public class NoKeyInDatabaseException extends RuntimeException {
    public NoKeyInDatabaseException(Integer id) {
        super("There's no key " + id.toString() + " in the database");
    }
}
