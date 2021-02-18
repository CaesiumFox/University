package io.github.caesiumfox.lab05.exceptions;

public class ShellSignalExitException extends Exception {
    public ShellSignalExitException() {
        super("Exiting");
    }
}
