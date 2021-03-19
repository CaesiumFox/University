package io.github.caesiumfox.lab05.element;

import com.google.gson.annotations.SerializedName;
import io.github.caesiumfox.lab05.exceptions.WrongEnumInputException;

public enum Color {
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

    public static String listConstants() {
        return "(blue, green, red, white, yellow)";
    }
}
