package io.github.caesiumfox.lab07.common.entry;

import io.github.caesiumfox.lab07.common.exceptions.WrongEnumInputException;

import java.io.Serializable;

/**
 * Определяет один из пяти цветов:
 * green, red, blue, yellow, white
 */

public enum Color {
    GREEN {
        @Override
        public String toString() {
            return "Green";
        }
    },
    RED {
        @Override
        public String toString() {
            return "Red";
        }
    },
    BLUE {
        @Override
        public String toString() {
            return "Blue";
        }
    },
    YELLOW {
        @Override
        public String toString() {
            return "Yellow";
        }
    },
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

    public static Color fromOrdinal(int ordinal) {
        switch(ordinal) {
            case 0: return GREEN;
            case 1: return RED;
            case 2: return BLUE;
            case 3: return YELLOW;
            case 4: return WHITE;
            default:
                return GREEN;
        }
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
