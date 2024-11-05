package com.bignerdranch.android.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("last_updated_epoch") val date: Long,
    @SerializedName("temp_c") val tempC: Float,
    @SerializedName("condition") val conditionDto: ConditionDto,
    @SerializedName("is_day") val isDay: Int,
    @SerializedName("wind_dir") val windDirection: String,
    @SerializedName("wind_kph") val windKph: Float,
    @SerializedName("feelslike_c") val feelsLikeC: Float,
    @SerializedName("pressure_mb") val pressureMb: Float,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("gust_kph") val gustKph: Float,
    @SerializedName("dewpoint_c") val dewPoint: Float,
    @SerializedName("vis_km") val visibilityKm: Float,
    @SerializedName("precip_mm") val precipitationsMm: Float,
)
