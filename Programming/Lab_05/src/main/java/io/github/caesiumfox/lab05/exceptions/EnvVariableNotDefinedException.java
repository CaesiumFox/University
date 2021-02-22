package io.github.caesiumfox.lab05.exceptions;

public class EnvVariableNotDefinedException extends Exception {
    public EnvVariableNotDefinedException(String varName) {
        super("Environment variable \"" + varName + "\" is not defined");
    }
}
