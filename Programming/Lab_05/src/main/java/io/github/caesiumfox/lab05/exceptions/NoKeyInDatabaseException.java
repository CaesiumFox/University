package io.github.caesiumfox.lab05.exceptions;

public class NoKeyInDatabaseException extends Exception {
    public NoKeyInDatabaseException(Integer id) {
        super("There's no key " + id.toString() + " in the database");
    }
}
