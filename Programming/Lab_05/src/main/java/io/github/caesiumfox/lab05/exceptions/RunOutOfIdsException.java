package io.github.caesiumfox.lab05.exceptions;

public class RunOutOfIdsException extends Exception {
    public RunOutOfIdsException() {
        super("The maximum ID value (" +
                String.valueOf(Integer.MAX_VALUE) +
                ") has been reached");
    }
}
