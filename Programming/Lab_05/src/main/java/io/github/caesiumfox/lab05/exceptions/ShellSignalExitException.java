package io.github.caesiumfox.lab05.exceptions;

public class ShellSignalExitException extends ShellSignalException {
    public ShellSignalExitException() {
        super("Exiting");
    }
}
