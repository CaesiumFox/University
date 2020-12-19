package io.github.caesiumfox.lab04.exceptions;

import io.github.caesiumfox.lab04.PhysicalObject;

public class EmptyNameException extends Exception {
    public EmptyNameException(PhysicalObject physObj) {
        super("Name not defined for " + physObj.toString());
    }
}
