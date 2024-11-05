package com.bignerdranch.android.weatherapp.domain.entity

import java.util.Calendar

data class CurrentWeather(
    val isDay: Boolean = true,
    val tempC: Float,
    val condition: String,
    val conditionUrl: String,
    val date: Calendar,
    val windDirection: WindDirection,
    val windKph: String,
    val windGustKph: String,
    val feelsLikeC: Float,
    val pressureMmM: Int,
    val humidity: Int,
    val dewPoint: Float,
    val visibilityKm: Int,
    val precipitationsMm: Int,
)
