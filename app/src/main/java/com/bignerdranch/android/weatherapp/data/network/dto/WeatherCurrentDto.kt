package com.bignerdranch.android.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherCurrentDto(
    @SerializedName("location") val locationInfo: LocationInfoDto,
    @SerializedName("current") val current: WeatherDto
)
