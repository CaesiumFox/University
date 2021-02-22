package io.github.caesiumfox.lab05.exceptions;

public class ShellSignalSaveException extends ShellSignalException {
    private String outputFile;

    public ShellSignalSaveException(String outputFile) {
        super("Saving...");
        this.outputFile = outputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }
}
