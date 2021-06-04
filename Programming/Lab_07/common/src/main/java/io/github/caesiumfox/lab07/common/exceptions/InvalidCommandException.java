package io.github.caesiumfox.lab07.common.exceptions;

/**
 * Выбрасывается, если было введено
 * неправильное имя команды.
 */
public class InvalidCommandException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если было введено
     * неправильное имя команды.
     * @param name Имя команды
     */
    public InvalidCommandException(String name) {
        super("Invalid command: " + name);
    }
}
