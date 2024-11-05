package com.bignerdranch.android.weatherapp.domain.entity

import java.util.Calendar

data class ForecastWeather(
    val date: Calendar,
    val avgTempC: String,
    val maxTempC: String,
    val minTempC: String,
    val maxWindKph: String,
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    val avgHumidity: Int,
    val avgVisibility: Int,
    val hourlyForecast: List<WeatherInHour>,
    val conditionImageUrl: String
)
