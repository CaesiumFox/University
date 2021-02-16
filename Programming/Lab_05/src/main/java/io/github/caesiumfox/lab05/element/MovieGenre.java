package io.github.caesiumfox.lab05.element;

import com.google.gson.annotations.SerializedName;

public enum MovieGenre {
    @SerializedName("action")
    ACTION,
    @SerializedName("tragedy")
    TRAGEDY,
    @SerializedName("horror")
    HORROR,
    @SerializedName("fantasy")
    FANTASY
}
