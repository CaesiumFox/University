package io.github.caesiumfox.lab06.common.entry;

import com.google.gson.annotations.SerializedName;
import io.github.caesiumfox.lab06.common.exceptions.WrongEnumInputException;

import java.io.Serializable;

/**
 * Определяет один из пяти цветов:
 * green, red, blue, yellow, white
 */

public enum Color implements Serializable {
    @SerializedName("green")
    GREEN {
        @Override
        public String toString() {
            return "Green";
        }
    },
    @SerializedName("red")
    RED {
        @Override
        public String toString() {
            return "Red";
        }
    },
    @SerializedName("blue")
    BLUE {
        @Override
        public String toString() {
            return "Blue";
        }
    },
    @SerializedName("yellow")
    YELLOW {
        @Override
        public String toString() {
            return "Yellow";
        }
    },
    @SerializedName("white")
    WHITE {
        @Override
        public String toString() {
            return "White";
        }
    };

    /**
     * Определяет цвет по его названию.
     * В отличие от {@link #valueOf(String)}
     * не чувствителен к регистру.
     * @param str Одно из значений:
     * "green", "red", "blue", "yellow", "white"
     * в любом регистре.
     * @return Получившийся цвет
     * @throws WrongEnumInputException Если строка не преобразуема в константу
     */
    public static Color fromString(String str) throws WrongEnumInputException {
        String loweredStr = str.toLowerCase().trim();
        switch(loweredStr) {
            case "green":
                return GREEN;
            case "red":
                return RED;
            case "blue":
                return BLUE;
            case "yellow":
                return YELLOW;
            case "white":
                return WHITE;
        }
        throw new WrongEnumInputException(str);
    }

    /**
     * Возвращает строку, содержащюю
     * все константы перечисления
     * {@link Color}, перечисленные в
     * алфавитном порядке в
     * нижнем регистре через запятую
     * и пробел и заключенные в
     * круглые скобки.
     * @return Описанная выше строка
     */
    public static String listConstants() {
        return "(blue, green, red, white, yellow)";
    }
}
