package com.bignerdranch.android.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class DayWeatherDto(
    @SerializedName("avgtemp_c") val tempC: Float,
    @SerializedName("mintemp_c") val minTempC: Float,
    @SerializedName("maxtemp_c") val maxTempC: Float,
    @SerializedName("maxwind_kph") val maxWindKph: Float,
    @SerializedName("avgvis_km") val avgVisibility: Float,
    @SerializedName("daily_chance_of_rain") val chanceOfRain: Int,
    @SerializedName("daily_chance_of_snow") val chanceOfSnow: Int,
    @SerializedName("condition") val conditionDto: ConditionDto,
    @SerializedName("avghumidity") val avgHumidity: Int
)
