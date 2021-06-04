package io.github.caesiumfox.lab07.common.exceptions;

/**
 * Выбрасывается, если программа не
 * может автоматически подобрать значение
 * ключа по причине достижения наибольшего
 * значения, которое может вместить в себя
 * тип Integer
 */
public class RunOutOfIdsException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если программа не
     * может автоматически подобрать значение
     * ключа по причине достижения наибольшего
     * значения, которое может вместить в себя
     * тип Integer
     */
    public RunOutOfIdsException() {
        super("The maximum ID value (" +
                String.valueOf(Integer.MAX_VALUE) +
                ") has been reached");
    }
}
