package io.github.caesiumfox.lab07.common.exceptions;

/**
 * Выбрасывается, если попытаться
 * обратиться к записи в базе данных
 * по несуществующему ключу или не
 * к своему элементу.
 */
public class NoKeyInDatabaseException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если попытаться
     * обратиться к записи в базе данных
     * по несуществующему ключу или не
     * к своему элементу.
     * @param id Ключ, при котором вызвано исключение
     */
    public NoKeyInDatabaseException(Integer id) {
        super("There's no key " + id + " in the database " +
                "or you are not an owner of the entry #" + id);
    }
}
