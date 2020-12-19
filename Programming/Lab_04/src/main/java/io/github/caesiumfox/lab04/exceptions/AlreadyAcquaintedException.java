package io.github.caesiumfox.lab04.exceptions;

import io.github.caesiumfox.lab04.characters.Human;

public class AlreadyAcquaintedException extends RuntimeException {
    public AlreadyAcquaintedException(Human first, Human second) {
        super(first.toString() + " is already acquainted with "
                + second.toString());
    }
}
