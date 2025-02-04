package com.bignerdranch.android.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class DayDto(
    @SerializedName("date_epoch") val date: Long,
    @SerializedName("day") val dayWeatherDto: DayWeatherDto,
    @SerializedName("astro") val astronomical: AstronomicalDto,
    @SerializedName("hour") val hourlyForecast: List<WeatherInHourDto>

)