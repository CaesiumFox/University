package io.github.caesiumfox.lab06.common.exceptions;

/**
 * Выбрасывается в случае,
 * если попытаться присвоить некоторому
 * числовому полю значение,
 * выходящее за пределы области допустимых значений.
 */
public class NumberOutOfRangeException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается в случае,
     * если попытаться присвоить некоторому
     * целочисленному полю значение,
     * выходящее за пределы области допустимых значений.
     * @param value Значение числа, оказавшееся недопустимым
     * @param minVal Наименьшее допустимое значение числа
     * @param maxVal Наибольшее допустимое значение числа
     */
    public NumberOutOfRangeException(long value, long minVal, long maxVal) {
        super("The value " + String.valueOf(value) + " is out of range [" +
                String.valueOf(minVal) + ", " + String.valueOf(maxVal) + "]");
    }
}
