package io.github.caesiumfox.lab07.common.entry;

import io.github.caesiumfox.lab07.common.exceptions.WrongEnumInputException;

/**
 * Определяет одну из четырёх возрастных
 * категорий MPAA: G, PG, PG-13, R.
 */
public enum MpaaRating {
    G {
        @Override
        public String toString() {
            return "G";
        }
    },
    PG {
        @Override
        public String toString() {
            return "PG";
        }
    },
    PG_13 {
        @Override
        public String toString() {
            return "PG-13";
        }
    },
    R {
        @Override
        public String toString() {
            return "R";
        }
    };

    /**
     * Определяет цвет по его названию.
     * В отличие от {@link #valueOf(String)}
     * не чувствителен к регистру.
     * @param str Одно из значений:
     * "g", "pg", "pg13", "pg-13", "pg_13", "r"
     * в любом регистре.
     * @return Получившийся цвет
     * @throws WrongEnumInputException Если строка не преобразуема в константу
     */
    public static MpaaRating fromString(String str) throws WrongEnumInputException {
        String loweredStr = str.toLowerCase().trim();
        switch(loweredStr) {
            case "g":
                return G;
            case "pg":
                return PG;
            case "pg13":
            case "pg-13":
            case "pg_13":
                return PG_13;
            case "r":
                return R;
        }
        throw new WrongEnumInputException(str);
    }

    public static MpaaRating fromOrdinal(int ordinal) {
        switch(ordinal) {
            case 0: return G;
            case 1: return PG;
            case 2: return PG_13;
            case 3: return R;
            default:
                return G;
        }
    }

    /**
     * Возвращает строку, содержащюю
     * все константы перечисления
     * {@link MpaaRating}, перечисленные в
     * порядке возрастания в
     * нижнем регистре через запятую
     * и пробел и заключенные в
     * круглые скобки.
     * @return Описанная выше строка
     */
    public static String listConstants() {
        return "(g, pg, pg-13, r)";
    }
}
