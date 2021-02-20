package io.github.caesiumfox.lab05.element;

import com.google.gson.annotations.SerializedName;

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
    }
}
