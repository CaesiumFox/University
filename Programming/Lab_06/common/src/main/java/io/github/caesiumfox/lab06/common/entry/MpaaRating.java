package io.github.caesiumfox.lab06.common.entry;

import com.google.gson.annotations.SerializedName;
import io.github.caesiumfox.lab06.common.exceptions.WrongEnumInputException;

import java.io.Serializable;

/**
 * Определяет одну из четырёх возрастных
 * категорий MPAA: G, PG, PG-13, R.
 */
public enum MpaaRating implements Serializable {
    @SerializedName("g")
    G {
        @Override
        public String toString() {
            return "G";
        }
    },
    @SerializedName("pg")
    PG {
        @Override
        public String toString() {
            return "PG";
        }
    },
    @SerializedName("pg13")
    PG_13 {
        @Override
        public String toString() {
            return "PG-13";
        }
    },
    @SerializedName("r")
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
