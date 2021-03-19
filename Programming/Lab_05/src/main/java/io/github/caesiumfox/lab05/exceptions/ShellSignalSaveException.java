package io.github.caesiumfox.lab05.exceptions;

import java.io.File;

public class ShellSignalSaveException extends ShellSignalException {
    private String outputFile;

    public ShellSignalSaveException(String outputFile) {
        super("Saving to \"" + new File(outputFile).getAbsolutePath() + "\" ...");
        this.outputFile = outputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }
}
