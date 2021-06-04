package io.github.caesiumfox.lab07.common.exceptions;

/**
 * Выбрасывается, если нужно послать
 * сигнал командной оболочке.
 */
public class ShellSignalException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если нужно послать
     * сигнал командной оболочке.
     * @param message Сообщение для вывода пользователю
     */
    public ShellSignalException(String message) {
        super(message);
    }
}
