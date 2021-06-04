package io.github.caesiumfox.lab07.common.exceptions;

import java.io.File;

/**
 * Выбрасывается, если нужно послать
 * сигнал сохранения файла
 * командной оболочке.
 */
public class ShellSignalSaveException extends ShellSignalException {
    private String outputFile;

    /**
     * Создаёт исключение, которое
     * выбрасывается, если нужно послать
     * сигнал сохранения файла
     * командной оболочке.
     * @param outputFile Путь к выходному файлу
     */
    public ShellSignalSaveException(String outputFile) {
        super("Saving to \"" + new File(outputFile).getAbsolutePath() + "\" ...");
        this.outputFile = outputFile;
    }

    /**
     * Возвращает путь к выходному файлу.
     * @return Путь к выходному файлу
     */
    public String getOutputFile() {
        return outputFile;
    }
}
