package io.github.caesiumfox.lab04.exceptions;

import io.github.caesiumfox.lab04.characters.Human;

public class AcquaintanceException extends RuntimeException {
    public AcquaintanceException(Human one, Human two) {
        super(one.toString() + " and " + two.toString() +
                " are already acquainted or the same person");
    }
}
