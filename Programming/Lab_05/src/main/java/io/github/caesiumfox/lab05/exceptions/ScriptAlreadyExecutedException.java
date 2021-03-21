package io.github.caesiumfox.lab05.exceptions;

public class ScriptAlreadyExecutedException extends Exception {
    public ScriptAlreadyExecutedException(String fullPath) {
        super("Script \"" + fullPath + "\" is already executed");
    }
}
