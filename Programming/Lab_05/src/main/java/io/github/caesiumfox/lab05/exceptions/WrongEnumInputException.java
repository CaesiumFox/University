package io.github.caesiumfox.lab05.exceptions;

public class WrongEnumInputException extends Exception {
    public WrongEnumInputException(String name) {
        super("The name \"" + name + "\" cannot be considered as an enumeration constant");
    }
}
