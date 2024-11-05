package com.bignerdranch.android.weatherapp.domain.entity

data class WeatherInHour(
    val time: String,
    val tempC: String = "",
    val conditionUrl: String = "",
    val isSunrise: Boolean = false,
    val isSunset: Boolean = false,
)
