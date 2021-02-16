package io.github.caesiumfox.lab05.element;

import io.github.caesiumfox.lab05.exceptions.CoordinatesOutOfRangeException;

public class Coordinates {
    /**
     * Вспомогательный класс для работы с JSON файлами
     * при помощи библиотеки GSON.
     * GSON работает напосредственно с ним,
     * преобразование между "скелетом" и
     * базовым классом (Coordinates) происходит
     * отдельно.
     */
    public static class Skeleton {
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
     * ({@link #minX}, {link #minY}).
     */
    public Coordinates() {
        this.x = minX;
        this.y = minY;
    }

    /**
     * Конструктор, инициализирующий координаты
     * в соответствии с первичными данными,
     * полученными в результате чтения json файла
     * @param skeleton объект класса {@link io.github.caesiumfox.lab05.parsingSkeleton.Coordinates},
     *                 содержащий данные из json файла
     * @exception CoordinatesOutOfRangeException выбрасывается,
     * если координаты выходят за пределы областей допустимых значений.
     */
    public Coordinates(io.github.caesiumfox.lab05.parsingSkeleton.Coordinates skeleton) {
        setX(skeleton.x);
        setY(skeleton.y);
    }

    /**
     * Возвращает значение горизонтальной координаты
     * @return значение горизонтальной координаты
     * @see #getY()
     * @see #setX(float)
     * @see #setY(float)
     */
    public float getX() { return x; }

    /**
     * Возвращает значение вертикальной координаты
     * @return значение вертикальной координаты
     * @see #getX()
     * @see #setX(float)
     * @see #setY(float)
     */
    public float getY() { return y; }

    /**
     * Устанавливает значение горизонтальной координаты,
     * если оно находится в пределах [{@link #minX}, {@link #maxX}]
     * @param x значение горизонтальной координаты
     * @exception CoordinatesOutOfRangeException выбрасывается,
     * если аргумент выходит за пределы области допустимых значений.
     * @see #getX()
     * @see #getY()
     * @see #setY(float)
     */
    public void setX(float x) {
        if(x >= minX && x <= maxX) {
            this.x = x;
        } else {
            throw new CoordinatesOutOfRangeException(x, minX, maxX);
        }
    }

    /**
     * Устанавливает значение вертикальной координаты,
     * если оно находится в пределах [{@link #minY}, {@link #maxY}]
     * @param y значение вертикальной координаты
     * @exception CoordinatesOutOfRangeException выбрасывается,
     * если аргумент выходит за пределы области допустимых значений.
     * @see #getX()
     * @see #getY()
     * @see #setX(float)
     */
    public void setY(float y) {
        if(y >= minY && y <= maxY) {
            this.y = y;
        } else {
            throw new CoordinatesOutOfRangeException(y, minY, maxY);
        }
    }
}
