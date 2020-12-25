package io.github.caesiumfox.lab04.exceptions;

import io.github.caesiumfox.lab04.Mammal;
import io.github.caesiumfox.lab04.PhysicalObject;

public class NoThoughtException extends RuntimeException {
    public NoThoughtException(Mammal object) {
        super(object.toString() + "has nothing to think about");
    }
}
