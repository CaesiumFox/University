package io.github.caesiumfox.lab05.exceptions;

public class ElementIdAlreadyExistsException extends Exception {
    public ElementIdAlreadyExistsException(Integer id) {
        super("Movie with ID " + String.valueOf(id) +
                " already presents in database");
    }
}
