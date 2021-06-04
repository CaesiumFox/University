package io.github.caesiumfox.lab07.common.exceptions;

public class NoRecentClientException extends RuntimeException {
    public NoRecentClientException() {
        super("No client is registered as recent");
    }
}
