package io.github.caesiumfox.lab05.element;

import com.google.gson.annotations.SerializedName;

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
    }
}
