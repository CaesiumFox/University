package io.github.caesiumfox.lab05.element;

import com.google.gson.annotations.SerializedName;
import io.github.caesiumfox.lab05.exceptions.WrongEnumInputException;

/**
 * Определяет один из четырёх жанров:
 * action, tragedy, horror, fantasy
 */

public enum MovieGenre {
    @SerializedName("action")
    ACTION {
        @Override
        public String toString() {
            return "Action";
        }
    },
    @SerializedName("tragedy")
    TRAGEDY  {
        @Override
        public String toString() {
            return "Tragedy";
        }
    },
    @SerializedName("horror")
    HORROR {
        @Override
        public String toString() {
            return "Horror";
        }
    },
    @SerializedName("fantasy")
    FANTASY {
        @Override
        public String toString() {
            return "Fantasy";
        }
    };

    /**
     * Определяет жанр по его названию.
     * В отличие от {@link #valueOf(String)}
     * не чувствителен к регистру.
     * @param str Одно из значений:
     * "action", "tragedy", "horror", "fantasy"
     * в любом регистре.
     * @return Получившийся жанр
     * @throws WrongEnumInputException Если строка не преобразуема в константу
     */
    public static MovieGenre fromString(String str) throws WrongEnumInputException {
        String loweredStr = str.toLowerCase().trim();
        switch(loweredStr) {
            case "action":
                return ACTION;
            case "tragedy":
                return TRAGEDY;
            case "horror":
                return HORROR;
            case "fantasy":
                return FANTASY;
        }
        throw new WrongEnumInputException(str);
    }

    /**
     * Возвращает строку, содержащюю
     * все константы перечисления
     * {@link MovieGenre}, перечисленные в
     * алфавитном порядке в
     * нижнем регистре через запятую
     * и пробел и заключенные в
     * круглые скобки.
     * @return Описанная выше строка
     */
    public static String listConstants() {
        return "(action, fantasy, horror, tragedy)";
    }
}
