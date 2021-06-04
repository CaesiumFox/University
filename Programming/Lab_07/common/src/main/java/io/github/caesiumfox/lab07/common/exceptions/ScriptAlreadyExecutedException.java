package io.github.caesiumfox.lab07.common.exceptions;

/**
 * Выбрасывается, если попытаться вызвать
 * скрипт рекурсивно.
 */
public class ScriptAlreadyExecutedException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если попытаться вызвать
     * скрипт рекурсивно.
     * @param fullPath Полый путь до скрипта
     */
    public ScriptAlreadyExecutedException(String fullPath) {
        super("Script \"" + fullPath + "\" is already executed");
    }
}
