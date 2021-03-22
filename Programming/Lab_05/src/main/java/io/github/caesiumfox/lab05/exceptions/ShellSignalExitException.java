package io.github.caesiumfox.lab05.exceptions;

/**
 * Выбрасывается, если нужно послать
 * сигнал выхода из программы
 * командной оболочке.
 */
public class ShellSignalExitException extends ShellSignalException {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если нужно послать
     * сигнал выхода из программы
     * командной оболочке.
     */
    public ShellSignalExitException() {
        super("Exiting");
    }
}
