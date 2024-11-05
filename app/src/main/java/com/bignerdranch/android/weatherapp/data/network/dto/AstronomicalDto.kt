package com.bignerdranch.android.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class AstronomicalDto(
    @SerializedName("sunrise") val sunrise: String,
    @SerializedName("sunset") val sunset: String,
)