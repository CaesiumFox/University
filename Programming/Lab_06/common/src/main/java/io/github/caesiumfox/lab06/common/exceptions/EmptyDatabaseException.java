package io.github.caesiumfox.lab06.common.exceptions;

/**
 * Выбрасывается, если была выполнена
 * попытка получить элемент из пустой
 * базы данных.
 */
public class EmptyDatabaseException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если была выполнена
     * попытка получить элемент из пустой
     * базы данных.
     */
    public EmptyDatabaseException() {
        super("The database is empty");
    }
}
