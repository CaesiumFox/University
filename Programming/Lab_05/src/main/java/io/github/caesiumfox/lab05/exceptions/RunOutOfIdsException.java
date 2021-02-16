package io.github.caesiumfox.lab05.exceptions;

public class RunOutOfIdsException extends RuntimeException {
    public RunOutOfIdsException() {
        super("The maximum ID value (" +
                String.valueOf(Integer.MAX_VALUE) +
                ") has been reached");
    }
}
