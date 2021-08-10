package io.github.caesiumfox.lab07.common.exceptions;

public class NotAnOwnerException extends Exception {
    public NotAnOwnerException(String username, int id) {
        super(username + " is not an owner of the element #" + id);
    }
}
