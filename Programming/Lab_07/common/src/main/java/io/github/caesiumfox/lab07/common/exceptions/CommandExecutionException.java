package io.github.caesiumfox.lab07.common.exceptions;

/**
 * Выбрасывается, если в процессе самого выполнения
 * команды произошла ошибка.
 * Является обёрткой для исключений.
 */
public class CommandExecutionException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если в процессе самого выполнения
     * команды произошла ошибка и является
     * обёрткой других исключений.
     * @param child Исключение, обёрткой которого является
     * это исключение.
     */
    public CommandExecutionException(Exception child) {
        super("Execution exception: " + child.getMessage());
    }
}
