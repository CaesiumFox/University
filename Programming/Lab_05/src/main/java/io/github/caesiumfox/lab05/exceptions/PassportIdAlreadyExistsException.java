package io.github.caesiumfox.lab05.exceptions;

public class PassportIdAlreadyExistsException extends Exception {
    public PassportIdAlreadyExistsException(String passportID) {
        super("Movie with ID " + passportID + " already presents in database");
    }
}
