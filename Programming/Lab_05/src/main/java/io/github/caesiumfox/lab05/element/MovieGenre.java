package io.github.caesiumfox.lab05.element;

import com.google.gson.annotations.SerializedName;

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
    }
}
