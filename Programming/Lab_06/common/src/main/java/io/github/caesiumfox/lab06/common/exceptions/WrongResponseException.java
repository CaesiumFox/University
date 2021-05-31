package io.github.caesiumfox.lab06.common.exceptions;

import io.github.caesiumfox.lab06.common.KeyWord;

public class WrongResponseException extends RuntimeException {
    public WrongResponseException(KeyWord response) {
        super("Wrong response from server: " + response.toString());
    }
}
