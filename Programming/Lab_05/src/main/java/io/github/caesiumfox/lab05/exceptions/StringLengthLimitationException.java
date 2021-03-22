package io.github.caesiumfox.lab05.exceptions;

/**
 * Выбрасывается в случае,
 * если попытаться присвоить некоторому
 * строковому полю значение, длина которого
 * выходит за пределы области допустимых значений.
 */
public class StringLengthLimitationException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается в случае,
     * если попытаться присвоить некоторому
     * строковому полю значение, длина которого
     * выходит за пределы области допустимых значений.
     * @param str Строка, длина которой недопустима
     * @param minLen Наименьшая допустимая длина строки
     * @param maxLen Наибольшая допустимая длина строки
     */
    public StringLengthLimitationException(String str, int minLen, int maxLen) {
        super("String \"" + str + "\" length is out of range ["
                + String.valueOf(minLen) + ", "
                + (maxLen < 0 ? "infinity" : String.valueOf(maxLen)) + "]");
    }
}
