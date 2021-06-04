package io.github.caesiumfox.lab07.common.exceptions;

/**
 * Выбрасывается, если заданную
 * строку нельзя интерпретировать
 * как константу перечисления
 * без учёта регистра.
 * @see io.github.caesiumfox.lab05.element.Color#fromString(String)
 * @see io.github.caesiumfox.lab05.element.MovieGenre#fromString(String)
 * @see io.github.caesiumfox.lab05.element.MpaaRating#fromString(String)
 */
public class WrongEnumInputException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если заданную
     * строку нельзя интерпретировать
     * как константу перечисления
     * без учёта регистра.
     * @param name Неправильное имя константы
     */
    public WrongEnumInputException(String name) {
        super("The name \"" + name + "\" cannot be considered as an enumeration constant");
    }
}
