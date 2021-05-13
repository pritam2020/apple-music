package com.example.apple

import com.google.gson.annotations.SerializedName

data class model (
    @SerializedName("resultCount") val resultCount : Int,
    @SerializedName("results") val results : List<Results>
)