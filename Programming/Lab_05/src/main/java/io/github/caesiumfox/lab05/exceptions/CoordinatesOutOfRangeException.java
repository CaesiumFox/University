package io.github.caesiumfox.lab05.exceptions;

/**
 * Выбрасывается в случае,
 * если попытаться присвоить полям класса
 * {@link io.github.caesiumfox.lab05.element.Coordinates}
 * значения, выходящие за пределы области допустимых значений.
 */
public class CoordinatesOutOfRangeException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается в случае,
     * если попытаться присвоить полям класса
     * {@link io.github.caesiumfox.lab05.element.Coordinates}
     * значения, выходящие за пределы области допустимых значений.
     * @param value Значение координаты, оказавшееся недопустимым
     * @param min Наименьшее допустимое значение координаты
     * @param max Наибольшее допустимое значение координаты
     */
    public CoordinatesOutOfRangeException(float value, float min, float max) {
        super("Coordinate value " + String.valueOf(value) + " is out of range [" +
                String.valueOf(min) + ", " + String.valueOf(max) + "]");
    }
}
