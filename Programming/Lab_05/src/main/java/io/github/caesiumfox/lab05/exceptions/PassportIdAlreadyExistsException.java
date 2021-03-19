package io.github.caesiumfox.lab05.exceptions;

public class PassportIdAlreadyExistsException extends Exception {
    public PassportIdAlreadyExistsException(String passportID) {
        super("Passport ID " + passportID + " already registered");
    }
}
