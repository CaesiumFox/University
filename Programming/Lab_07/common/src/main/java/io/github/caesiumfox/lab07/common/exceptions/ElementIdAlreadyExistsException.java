package io.github.caesiumfox.lab07.common.exceptions;

/**
 * Выбрасывается, если попытаться
 * создать в базе данных элемент с ключём,
 * который уже имеется в базе данных.
 */
public class ElementIdAlreadyExistsException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если попытаться
     * создать в базе данных элемент с ключём,
     * который уже имеется в базе данных.
     * @param id Уже существующий ключ
     */
    public ElementIdAlreadyExistsException(Integer id) {
        super("Movie with ID " + String.valueOf(id) +
                " is already present in the database");
    }
}
