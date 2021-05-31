package io.github.caesiumfox.lab06.common.exceptions;

/**
 * Выбрасывается, если попытаться
 * обратиться к записи в базе данных
 * по несуществующему ключу.
 */
public class NoKeyInDatabaseException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если попытаться
     * обратиться к записи в базе данных
     * по несуществующему ключу.
     * @param id Ключ, которого нет в базе данных
     */
    public NoKeyInDatabaseException(Integer id) {
        super("There's no key " + id.toString() + " in the database");
    }
}
