package io.github.caesiumfox.lab05.element;

import com.google.gson.annotations.SerializedName;

public enum MpaaRating {
    @SerializedName("g")
    G,
    @SerializedName("pg")
    PG,
    @SerializedName("pg13")
    PG_13,
    @SerializedName("r")
    R
}
