package io.github.caesiumfox.lab05.element;

import com.google.gson.annotations.SerializedName;
import io.github.caesiumfox.lab05.exceptions.WrongEnumInputException;

public enum MpaaRating {
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

    public static MpaaRating fromString(String str) throws WrongEnumInputException {
        String loweredStr = str.toLowerCase();
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

    public static String listConstants() {
        return "(g, pg, pg-13, r)";
    }
}
