package io.github.caesiumfox.lab06.common.exceptions;

/**
 * Выбрасывается, если попытаться внести в
 * базу данных с уже существующим номером паспорта.
 */
public class PassportIdAlreadyExistsException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если попытаться внести в
     * базу данных с уже существующим номером паспорта.
     * @param passportID Уже существующий номер паспорта
     */
    public PassportIdAlreadyExistsException(String passportID) {
        super("Passport ID " + passportID + " already registered");
    }
}
