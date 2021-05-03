package io.github.caesiumfox.lab06.common.exceptions;

public class NoRecentClientException extends RuntimeException {
    public NoRecentClientException() {
        super("No client is registered as recent");
    }
}
