package io.github.caesiumfox.lab04.exceptions;

import io.github.caesiumfox.lab04.PhysicalObject;

public class EmptyNameException extends Exception {
    public EmptyNameException(PhysicalObject object) {
        super(object.toString() + " has no name");
    }
}
