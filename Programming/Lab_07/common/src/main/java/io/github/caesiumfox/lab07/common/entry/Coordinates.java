package io.github.caesiumfox.lab07.common.entry;

import io.github.caesiumfox.lab07.common.exceptions.CoordinatesOutOfRangeException;

import java.io.Serializable;

/**
 * Класс, описывающий координаты
 */
public class Coordinates {
    /**
     * Вспомогательный класс для работы с JSON файлами
     * при помощи библиотеки GSON.
     * GSON работает напосредственно с ним,
     * преобразование между ним и
     * основным классом ({@link Coordinates}) происходит
     * отдельно.
     */
    public static class RawData implements Serializable {
        public float x;
        public float y;
    }


    private float x;
    private float y;

    /**
     * Максимальное значение горизонтальной координаты
     */
    public static final float maxX = 674;
    /**
     * Минимальное значение горизонтальной координаты
     */
    public static final float minX = 0;
    /**
     * Максимальное значение вертикальной координаты
     */
    public static final float maxY = 442;
    /**
     * Минимальное значение вертикальной координаты
     */
    public static final float minY = 0;

    /**
     * Конструктор по умолчанию.
     * Инициализирует координаты как
     * ({@link #minX}, {@link #minY}).
     */
    public Coordinates() {
        this.x = minX;
        this.y = minY;
    }

    /**
     * Конструктор, инициализирующий координаты
     * в соответствии с первичными данными,
     * полученными в результате чтения json файла
     *
     * @param rawData Объект класса {@link RawData},
     *                содержащий данные из json файла
     * @throws CoordinatesOutOfRangeException Выбрасывается,
     *                                        если координаты выходят за пределы областей допустимых значений.
     */
    public Coordinates(RawData rawData) throws CoordinatesOutOfRangeException {
        setX(rawData.x);
        setY(rawData.y);
    }

    /**
     * Возвращает значение горизонтальной координаты
     *
     * @return Значение горизонтальной координаты
     * @see #getY()
     * @see #setX(float)
     * @see #setY(float)
     */
    public float getX() {
        return x;
    }

    /**
     * Возвращает значение вертикальной координаты
     *
     * @return Значение вертикальной координаты
     * @see #getX()
     * @see #setX(float)
     * @see #setY(float)
     */
    public float getY() {
        return y;
    }

    /**
     * Устанавливает значение горизонтальной координаты,
     * если оно находится в пределах [{@link #minX}, {@link #maxX}]
     *
     * @param x Вначение горизонтальной координаты
     * @throws CoordinatesOutOfRangeException Выбрасывается,
     *                                        если аргумент выходит за пределы области допустимых значений.
     * @see #getX()
     * @see #getY()
     * @see #setY(float)
     */
    public void setX(float x) throws CoordinatesOutOfRangeException {
        if (x >= minX && x <= maxX) {
            this.x = x;
        } else {
            throw new CoordinatesOutOfRangeException(x, minX, maxX);
        }
    }

    /**
     * Устанавливает значение вертикальной координаты,
     * если оно находится в пределах [{@link #minY}, {@link #maxY}]
     *
     * @param y Значение вертикальной координаты
     * @throws CoordinatesOutOfRangeException Выбрасывается,
     *                                        если аргумент выходит за пределы области допустимых значений.
     * @see #getX()
     * @see #getY()
     * @see #setX(float)
     */
    public void setY(float y) throws CoordinatesOutOfRangeException {
        if (y >= minY && y <= maxY) {
            this.y = y;
        } else {
            throw new CoordinatesOutOfRangeException(y, minY, maxY);
        }
    }

    /**
     * Преобразует объект класса в
     * соответсвующий ему объект
     * класса {@link RawData}
     *
     * @return Объект класса {@link RawData}
     */
    public RawData toRawData() {
        RawData rawData = new RawData();
        rawData.x = this.x;
        rawData.y = this.y;
        return rawData;
    }
}
