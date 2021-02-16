package io.github.caesiumfox.lab05.exceptions;

/**
 * Класс непроверяемого исключения, выбрасываемый в случае,
 * если попытаться присвоить полям класса
 * {@link io.github.caesiumfox.lab05.element.Coordinates}
 * значения, выходящие за пределы области допустимых значений.
 */
public class CoordinatesOutOfRangeException extends RuntimeException {
    /**
     * Создаёт новое исключение
     * @param value значение координаты, оказавшееся недопустимым
     * @param min наименьшее допустимое значение координаты
     * @param max наибольшее допустимое значение координаты
     */
    public CoordinatesOutOfRangeException(float value, float min, float max) {
        super("Coordinate value " + String.valueOf(value) + " is out of range [" +
                String.valueOf(min) + ", " + String.valueOf(max) + "]");
    }
}
