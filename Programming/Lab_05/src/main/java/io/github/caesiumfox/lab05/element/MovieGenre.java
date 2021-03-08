package io.github.caesiumfox.lab05.element;

import com.google.gson.annotations.SerializedName;
import io.github.caesiumfox.lab05.exceptions.WrongEnumInputException;

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

    public static MovieGenre fromString(String str) throws WrongEnumInputException {
        String loweredStr = str.toLowerCase();
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

    public static String listConstants() {
        return "(action, fantasy, horror, tragedy)";
    }
}
